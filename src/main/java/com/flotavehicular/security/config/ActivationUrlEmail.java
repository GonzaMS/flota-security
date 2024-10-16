package com.flotavehicular.security.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivationUrlEmail {

    private DiscoveryClient discoveryClient;

    public ActivationUrlEmail(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public String getDynamicActivationUrl(String serviceName) {
        // Busca las instancias del servicio registrado en Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            // Toma la primera instancia y construye la URL
            ServiceInstance instance = instances.get(0);
            return instance.getUri().toString() + "/activate-account";
        }
        throw new IllegalStateException("No instance of " + serviceName + " found in Eureka");
    }
}
