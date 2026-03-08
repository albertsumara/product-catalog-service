package io.git.albertsumara.pcms.catalogservice.config;

import io.git.albertsumara.pcms.catalogservice.model.Attribute;
import io.git.albertsumara.pcms.catalogservice.model.Producer;
import io.git.albertsumara.pcms.catalogservice.model.Product;
import io.git.albertsumara.pcms.catalogservice.repository.AttributeRepository;
import io.git.albertsumara.pcms.catalogservice.repository.ProducerRepository;
import io.git.albertsumara.pcms.catalogservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    public DataInitializer(ProducerRepository producerRepository,
                           ProductRepository productRepository,
                           AttributeRepository attributeRepository) {
        this.producerRepository = producerRepository;
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (producerRepository.getAllProducers().isEmpty()) {

            Producer lg = new Producer("LG");
            Producer samsung = new Producer("Samsung");
            Producer sony = new Producer("SONY");

            long lgId = producerRepository.saveProducer(lg);
            long samsungId = producerRepository.saveProducer(samsung);
            long sonyId = producerRepository.saveProducer(sony);

            Product lgTv = new Product(lgId, "TV", 1200.0);
            long lgTvId = productRepository.saveProduct(lgTv);
            attributeRepository.saveAttribute(new Attribute("model", "42UN700"), lgTvId);
            attributeRepository.saveAttribute(new Attribute("size", "42inch"), lgTvId);
            attributeRepository.saveAttribute(new Attribute("resolution", "1920x1080"), lgTvId);
            attributeRepository.saveAttribute(new Attribute("smartTV", "true"), lgTvId);

            Product lgFridge = new Product(lgId, "Fridge", 2200.0);
            long lgFridgeId = productRepository.saveProduct(lgFridge);
            attributeRepository.saveAttribute(new Attribute("model", "GR-D500"), lgFridgeId);
            attributeRepository.saveAttribute(new Attribute("capacity", "300L"), lgFridgeId);
            attributeRepository.saveAttribute(new Attribute("color", "silver"), lgFridgeId);

            Product samsungPhone = new Product(samsungId, "Phone", 1200.0);
            long samsungPhoneId = productRepository.saveProduct(samsungPhone);
            attributeRepository.saveAttribute(new Attribute("model", "Galaxy S26"), samsungPhoneId);
            attributeRepository.saveAttribute(new Attribute("batteryLife", "24h"), samsungPhoneId);
            attributeRepository.saveAttribute(new Attribute("color", "black"), samsungPhoneId);

            Product samsungFridge = new Product(samsungId, "Fridge", 2500.0);
            long samsungFridgeId = productRepository.saveProduct(samsungFridge);
            attributeRepository.saveAttribute(new Attribute("model", "RF50A"), samsungFridgeId);
            attributeRepository.saveAttribute(new Attribute("capacity", "350L"), samsungFridgeId);
            attributeRepository.saveAttribute(new Attribute("color", "white"), samsungFridgeId);

            Product sonyTv = new Product(sonyId, "TV", 2000.0);
            long sonyTvId = productRepository.saveProduct(sonyTv);
            attributeRepository.saveAttribute(new Attribute("model", "Bravia X90J"), sonyTvId);
            attributeRepository.saveAttribute(new Attribute("size", "55inch"), sonyTvId);
            attributeRepository.saveAttribute(new Attribute("resolution", "3840x2160"), sonyTvId);
            attributeRepository.saveAttribute(new Attribute("HDR", "true"), sonyTvId);

            Product sonyConsole = new Product(sonyId, "Console", 500.0);
            long sonyConsoleId = productRepository.saveProduct(sonyConsole);
            attributeRepository.saveAttribute(new Attribute("model", "PS5"), sonyConsoleId);
            attributeRepository.saveAttribute(new Attribute("storage", "825GB"), sonyConsoleId);
            attributeRepository.saveAttribute(new Attribute("color", "white"), sonyConsoleId);
        }
    }
}