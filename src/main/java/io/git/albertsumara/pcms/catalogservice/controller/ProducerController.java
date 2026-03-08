package io.git.albertsumara.pcms.catalogservice.controller;
import io.git.albertsumara.pcms.catalogservice.model.Producer;
import io.git.albertsumara.pcms.catalogservice.service.ProducerService;
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

    @DeleteMapping("/{producer_id}")
    public ResponseEntity<?> deleteProducer(@PathVariable long producer_id){

        return ResponseEntity.ok("Producer deleted: " + producerService.deleteProducer(producer_id).toJson());

    }

}
