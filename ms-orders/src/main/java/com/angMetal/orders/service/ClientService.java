package com.angMetal.orders.service;

import com.angMetal.orders.entity.Client;
import com.angMetal.orders.entity.Company;
import com.angMetal.orders.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Create or Update a Client
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    // Get all Clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get a Client by ID
    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    // Delete a Client by ID
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }



    public Client updateClient(Long id, Client client) {
        // Fetch the existing client from the repository
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));

        // Update the properties of the existing client
        existingClient.setName(client.getName());
        existingClient.setAdresse(client.getAdresse());
        existingClient.setEmail(client.getEmail());
        existingClient.setNumeroTel(client.getNumeroTel());
        existingClient.setTypeClient(client.getTypeClient());
        // Save the updated client back to the repository
        return clientRepository.save(existingClient);
    }

}
