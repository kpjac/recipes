package com.fsd01.recipes.controller;

import com.fsd01.recipes.message.ResponseMessage;
import com.fsd01.recipes.model.*;
import com.fsd01.recipes.repository.IngredientRepository;
import com.fsd01.recipes.repository.CommentRepository;
import com.fsd01.recipes.repository.RecipeMadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;
import javax.measure.Unit;
import static tec.units.ri.unit.MetricPrefix.*;

import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;
import systems.uom.unicode.CLDR;
import systems.uom.common.USCustomary;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ImageStorageService storageService;

    @Autowired
    private IngredientRepository ingredientRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private RecipeMadeRepository recipeMadeRepo;

    final Unit<Volume> MILLILITRE = MILLI(Units.LITRE);

    User getCurrentUser() {
        return ((RecipeUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUser();
    }

    Boolean userAnon() {
        return (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }


    @GetMapping("/")
    public String viewHomePage(Model model) {
        if (!userAnon()) {
            User currentUser = getCurrentUser();
            String name = currentUser.getDisplayName();
            model.addAttribute("userDisplayName", name);
            Category prefCat = null;
            if (recipeService.getPreferredCategory(currentUser) != null) {
                prefCat = recipeService.getPreferredCategory(currentUser);
            }
            List<Cuisine> prefCuisines = null;
            if (!recipeService.getTop3Cuisines(currentUser).isEmpty()) {
                prefCuisines = recipeService.getTop3Cuisines(currentUser);
            }
            if (prefCat != null && !prefCuisines.isEmpty()) {
                model.addAttribute("recommended", recipeService.getRecommendedRecipes(prefCat, prefCuisines));
            } else {
                model.addAttribute("recommended", null);
            }
        }
        model.addAttribute("newRecipes", recipeService.getRecipeRepo().findTop8ByOrderByCreateTimeDesc());
        model.addAttribute("popularRecipes", recipeService.getRecipeRepo().findTop8ByOrderByLikesDesc());
        model.addAttribute("holidayRecipes", recipeService.getRecipeRepo().findTop8ByCategoryOrderByLikes(Category.HOLIDAY));

        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!userAnon()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/processRegister")
    public String processRegister(HttpServletRequest request, @Valid User user, BindingResult result, Model model) {

        for (FieldError error : result.getFieldErrors()) {
            System.out.println(error.getDefaultMessage());
        }

        if (result.hasErrors()) {
            return "register";
        }

        List<String> otherErrors = new ArrayList<>();

        Optional<User> userOptional = userService.getUserRepo().findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            otherErrors.add("Email is already registered");
        }

        if (!user.getPasswordEntry().equals(user.getPasswordRepeat())) {
            otherErrors.add("Passwords do not match");
        }

        if (!otherErrors.isEmpty()) {
            model.addAttribute("otherErrors", otherErrors);
            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPasswordEntry());
        user.setPassword(encodedPassword);

        userService.addUser(user);

        try {
            request.login(user.getEmail(), user.getPasswordEntry());
        } catch (ServletException e) {
            System.out.println(e.getMessage());
        }

        return "accountCreated";
    }

    @GetMapping("/addRecipe")
    public String showRecipeForm(Model model) {

        Recipe recipe = new Recipe();

        model.addAttribute("recipe", recipe);

        return "addRecipe";
    }

    @PostMapping("/processRecipe")
    public String processRecipe(@Valid Recipe recipe, @RequestParam("image") MultipartFile imageFile, @RequestParam Map<String,Object> requestParams, Model model) {

        // Assign to logged-in user
        recipe.setCreator(getCurrentUser());

        recipeService.saveRecipe(recipe);

        // Process image
        ResponseEntity responseEntity;
        Image image = null;
        recipe.setImages(new ArrayList<>());
        recipe.setTimesMade(new HashSet<>());
        String message;
        try {
            image = storageService.store(imageFile, recipe);

            message = "Uploaded the file successfully: " + imageFile.getOriginalFilename();
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + imageFile.getOriginalFilename() + "!";
            responseEntity = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
        if (image != null) {
            recipe.addImage(image);
        }

        recipeService.saveRecipe(recipe);

        // Process ingredients
        recipe.setIngredients(new ArrayList<>());
        Ingredient ingredient;
        Unit<Volume> unit;
        Quantity<Volume> quantity;
        for (int i = 1; i <= parseInt((String)requestParams.get("ingCount")); i++) {
            ingredient = null;
            unit = null;
            quantity = null;
            unit = switch ((String) requestParams.get("ingUnit" + i)) {
                case "TEASPOON" -> USCustomary.TEASPOON;
                case "TABLESPOON" -> USCustomary.TABLESPOON;
                case "CUP" -> USCustomary.CUP;
                case "FLUID_OUNCE" -> USCustomary.FLUID_OUNCE;
                case "PINT" -> USCustomary.PINT;
                case "QUART" -> CLDR.QUART;
                case "LITRE" -> Units.LITRE;
                case "MILLILITRE" -> MILLILITRE;
                default -> unit;
            };
            System.out.println(unit);
            System.out.println(i);
            if (unit != null) {
                quantity = Quantities.getQuantity(parseDouble((String)requestParams.get("ingAmount" + i)), unit);
                ingredient = new Ingredient(recipe, quantity, (String)requestParams.get("ingName" + i));
                ingredient.setUnit(IngredientUnit.valueOf((String)requestParams.get("ingUnit" + i)));
                ingredient.setAmount(parseDouble((String)requestParams.get("ingAmount" + i)));

            } else if (requestParams.get("ingAmount" + i) != null) {
                ingredient = new Ingredient(recipe, parseDouble((String)requestParams.get("ingAmount" + i)), (String)requestParams.get("ingName" + i));
                ingredient.setUnit(IngredientUnit.NONE);

            } else {
                ingredient = new Ingredient(recipe, (String)requestParams.get("ingName" + i));
                ingredient.setUnit(IngredientUnit.NONE);
            }
            ingredientRepo.save(ingredient);
            recipe.addIngredient(ingredient);
        }

        // Process steps
        recipe.setSteps(new ArrayList<>());
        for (int i = 1; i <= parseInt((String)requestParams.get("stepCount")); i++) {
            recipe.addStep((String)requestParams.get("step" + i));
        }

        recipeService.saveRecipe(recipe);

        model.addAttribute("uploadResponse", responseEntity);
        model.addAttribute("recipeAdded", true);
        model.addAttribute("recipe", recipe);
        model.addAttribute("image", image);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", new ArrayList());
        return "recipe";
    }

    @GetMapping("/recipe")
    public String showRecipe(Model model, @RequestParam Long id) {

        Optional<Recipe> lookForRecipe = recipeService.getRecipeById(id);
        if (!lookForRecipe.isPresent()) {
            return "404NotFound";
        }
        Recipe recipe = lookForRecipe.get();

        Image image = recipe.getImages().get(0);

        // If a user is logged in, check whether they have made this recipe.
        // Used for recipe-made badge.
        if (!userAnon()) {
            Optional<RecipeMade> recipeMade = recipeMadeRepo.findByRecipeAndUser(recipe.getId(), getCurrentUser().getId());
            if (recipeMade.isPresent()) {
                model.addAttribute("madeByUser", true);
            }
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("comments", commentRepo.findByRecipeOrderByTimestampDesc(recipe));
        model.addAttribute("comment", new Comment());
        model.addAttribute("image", image);


        return "recipe";

    }

    @PostMapping("/process_newComment")
    public String processNewComment(@Valid Comment comment, @RequestParam Long id, BindingResult result, Model model) {
        Recipe recipe = recipeService.getRecipeById(id).get();
        model.addAttribute("recipe", recipe);

        for (FieldError error : result.getFieldErrors()) {
            System.out.println(error.getDefaultMessage());
        }

        if (result.hasErrors()) {
            return "recipe";
        }

        User author = getCurrentUser();

        if (author.getComments() == null) {
            author.setComments(new ArrayList<>());
        }

        comment.setAuthor(author);
        comment.setRecipe(recipe);
        recipe.addComment(comment);
        author.addComment(comment);

        recipeService.saveRecipe(recipe);

        model.addAttribute("commentPosted", true);

        return "redirect:/recipe?id=" + id;

    }

    @PostMapping("/processLike")
    @ResponseBody
    public ResponseEntity<String> processLike(@RequestParam(name="recipeId") String recipeId) {
        if (userAnon()) {
            return new ResponseEntity<>("No user logged in", HttpStatus.UNAUTHORIZED);
        }
        long id = parseInt(recipeId);
        Recipe recipeLiked = recipeService.getRecipeById(id).get();
        recipeLiked.like();
        recipeService.saveRecipe(recipeLiked);
        return new ResponseEntity<>("Like processed", HttpStatus.CREATED);
    }

    @PostMapping("/processRecipeMade")
    @ResponseBody
    public ResponseEntity<String> processRecipeMade(@RequestParam(name="recipeId") String recipeId) {
        if (userAnon()) {
            return new ResponseEntity<>("No user logged in", HttpStatus.UNAUTHORIZED);
        }

        long id = parseInt(recipeId);
        Recipe recipeMade = recipeService.getRecipeById(id).get();
        User recipeMaker = getCurrentUser();

        Optional<RecipeMade> searchForExisting = recipeMadeRepo.findByRecipeAndUser(recipeMade.getId(), recipeMaker.getId());

        if (searchForExisting.isPresent()) {
            return new ResponseEntity<>("Recipe already made by this user", HttpStatus.FORBIDDEN);
        }

        recipeMadeRepo.save(new RecipeMade(recipeMaker, recipeMade));
        return new ResponseEntity<>("Recipe-made processed", HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public String showProfile(Model model, @RequestParam Long id){
        User user = userService.getUserRepo().getById(id);
        List<RecipeMade> made = recipeMadeRepo.findRecipeMadesByUser(user);
        List<Recipe> recipesMade = new ArrayList<>();
        for (RecipeMade rm : made) {
            recipesMade.add(rm.getRecipe());
        }
        List<Recipe> recipes = recipeService.getUserRepo().findAllByCreator(user);
        model.addAttribute("user", user);
        model.addAttribute("recipesMade", recipesMade);
        model.addAttribute("recipes", recipes);

        return "profile";
    }

    @GetMapping("/myProfile")
    public String showMyProfile(Model model){
        return showProfile(model, getCurrentUser().getId());
    }

    @PostMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<Recipe> searchResults = recipeService.getRecipeSearchResults(q.toLowerCase());
        if (searchResults.isEmpty()) {
            return "noResults";
        }
        model.addAttribute("recipes", searchResults);
        model.addAttribute("q", q);
        return "search";
    }

    @GetMapping("/recipes")
    public String showCategory(@RequestParam String c, Model model) {
        Category category = Category.valueOf(c.toUpperCase());
        model.addAttribute("recipes", recipeService.getRecipeCategory(category));
        model.addAttribute("category", c);
        return "recipes";
    }

    @GetMapping("/users")
    public String showUsersAdmin(Model model) {
        List<User> users = userService.getUserRepo().findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/lockUser")
    @ResponseBody
    public ResponseEntity<String> lockUser(@RequestParam(name="id") Long id) {
        User user = userService.getUserRepo().getById(id);
        user.setLocked(true);
        userService.addUser(user);
        return new ResponseEntity<>("User locked", HttpStatus.OK);
    }


}
