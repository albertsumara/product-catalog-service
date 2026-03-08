package io.git.albertsumara.pcms.catalogservice.service;

import io.git.albertsumara.pcms.catalogservice.model.Producer;
import io.git.albertsumara.pcms.catalogservice.model.Producer;
import io.git.albertsumara.pcms.catalogservice.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProducerService {

    ProducerRepository producerRepository;

    @Autowired
    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Producer createProducer(Map<String, String> attributes) {

        if(!attributes.containsKey("name")){
            throw new IllegalArgumentException("the producer's attribute set does not contain a mandatory key name.");
        }

        return new Producer(producerRepository.saveProducer(new Producer(attributes.get("name"))),
                attributes.get("name"));


    }

    public Producer deleteProducer(long producerId){
        Optional<Producer> result = producerRepository.findByIdAndDelete(producerId);
        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Producer id: " + producerId + " does not exist.");
        }
        return result.get();
    }

    public List<Producer> getAllProducers() {

        return producerRepository.getAllProducers();

    }

    public List<Producer> getProducer(long producerId) {
        Producer producer = producerRepository.findById(producerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producer id: " + producerId + " does not exist."
                ));
        return List.of(producer);
    }


}
