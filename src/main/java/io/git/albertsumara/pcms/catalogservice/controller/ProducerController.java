package io.git.albertsumara.pcms.catalogservice.controller;
import io.git.albertsumara.pcms.catalogservice.model.Producer;
import io.git.albertsumara.pcms.catalogservice.model.ProducerDisplayer;
import io.git.albertsumara.pcms.catalogservice.model.ProductDisplayer;
import io.git.albertsumara.pcms.catalogservice.model.ProductFilter;
import io.git.albertsumara.pcms.catalogservice.service.ProducerService;
import io.git.albertsumara.pcms.catalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("api/producer")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping()
    public ResponseEntity<?> createProducer(@RequestBody Map<String, String> attributes){

        try {
            Producer producer = producerService.createProducer(attributes);
            return ResponseEntity.ok(producer.toJson());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllProducers() {
        return ResponseEntity.ok(new ProducerDisplayer(producerService.getAllProducers()).toJson());
    }

    @GetMapping("/{producerId}")
    public ResponseEntity<?> getProducer(@PathVariable long producerId) {
        return ResponseEntity.ok(new ProducerDisplayer(producerService.getProducer(producerId)).toJson());
    }

    @GetMapping("/filter/{producerId}")
    public ResponseEntity<?> getFilteredProducer(@RequestParam Map<String, String> filters,
                                                 @PathVariable long producerId){
        ProductFilter filter = new ProductService().productFilter(filters);
        ProducerDisplayer producerDisplayer = new ProducerDisplayer(producerService.getProducer(producerId));
        producerDisplayer.setProductFilter(filter);
        return ResponseEntity.ok(producerDisplayer.toJson());
    }

    @DeleteMapping("/{producerId}")
    public ResponseEntity<?> deleteProducer(@PathVariable long producerId){

        return ResponseEntity.ok("Producer deleted: " + producerService.deleteProducer(producerId).toJson());

    }

}
