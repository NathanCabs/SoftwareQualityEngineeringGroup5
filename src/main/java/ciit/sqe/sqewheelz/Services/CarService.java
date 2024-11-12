package ciit.sqe.sqewheelz.Services;


import ciit.sqe.sqewheelz.Model.Car;
import ciit.sqe.sqewheelz.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

//    public List<Car> getAllCars(){
//        return carRepository.findAll();
//    }

    public List<Car> getCarsWithFilters(
            String brand, String model, String type, Integer seats,
            Double minPrice, Double maxPrice, int page, int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return carRepository.findCarsByFilters(brand, model, type, seats, minPrice, maxPrice, pageable).getContent();
    }

    public Optional<Car> getCarById(Long id){
        return carRepository.findById(id);
    }

    public Car createCar(Car car){
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car carDetails){
        return carRepository.findById(id).map(car -> {
            car.setBrand(carDetails.getBrand());
            car.setModel(carDetails.getModel());
            car.setType(carDetails.getType());
            car.setSeats(carDetails.getSeats());
            car.setPrice(carDetails.getPrice());
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car not found with id" + id));
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }
}