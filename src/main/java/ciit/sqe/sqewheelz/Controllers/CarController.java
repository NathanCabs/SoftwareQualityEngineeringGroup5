package ciit.sqe.sqewheelz.Controllers;


import ciit.sqe.sqewheelz.Model.Car;
import ciit.sqe.sqewheelz.Services.CarService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;


    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

//    @GetMapping
//    public List<Car> getAllCars(){
//        return carService.getAllCars();
//    }

    @GetMapping
    public List<Car> getAllCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer seats,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        if(size < 1){
            size = 10;
        }
            return carService.getCarsWithFilters(brand, model, type, seats, minPrice, maxPrice, page, size);
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id){
        return carService.getCarById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id" + id));
    }

    @PostMapping
    public String addCar(@RequestBody Car car, HttpServletRequest request){
        String role = (String) request.getAttribute("role");
        if(!"ADMIN".equals(role)){
         return "Access denied: Admins only!";
        }
         carService.createCar(car);
        return "Car created";
    }

    @PutMapping("/{id}")
    public String updateCar(@PathVariable Long id, @RequestBody Car carDetails, HttpServletRequest request){
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return "Access denied: Admins only";
        }
        carService.updateCar(id, carDetails);
        return "Car updated";
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id, HttpServletRequest request){
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return "Access denied: Admins only";
        }
        carService.deleteCar(id);
        return "Car deleted";
    }

}
