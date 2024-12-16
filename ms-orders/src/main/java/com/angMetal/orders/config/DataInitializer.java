package com.angMetal.orders.config;

import com.angMetal.orders.entity.*;
import enums.TypeClient;
import com.angMetal.orders.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Value("${application.createDummyCompanies}")
    private boolean createDummyCompanies;

    @Value("${application.createDummyBanks}")
    private boolean createDummyBanks;

    @Value("${application.createDummyProducts}")
    private boolean createDummyProducts;

    @Value("${application.createDummyClients}")
    private boolean createDummyClients;

    @Value("${application.createDummyFournisseurs}")
    private boolean createDummyFournisseurs;

    @PostConstruct
    private void init() {
        if (createDummyCompanies) {
            createDummyCompaniesIfNeeded();
        }
        if (createDummyBanks) {
            createDummyBanksIfNeeded();
        }

        if   (createDummyProducts){
            createDummyProductsIfNeeded();
        }
        if   (createDummyClients){
            createDummyClientsIfNeeded();
        }
        if   (createDummyFournisseurs){
            createDummyFournisseursIfNeeded();
        }

    }

    @Transactional
    private void createDummyCompaniesIfNeeded() {
        if (companyRepository.count() == 0) {
            List<Company> companies = Arrays.asList(
                    new Company("Tech Innovators", "Tunis", "Tech Innovators Co."),
                    new Company("Green Energy", "Sfax", "Green Energy Solutions"),
                    new Company("Smart Builders", "Nabeul", "Smart Builders Co."),
                    new Company("Algerian Imports", "Tunis", "Algerian Imports Ltd."),
                    new Company("Arab Logistics", "Sousse", "Arab Logistics Inc."),
                    new Company("Med Tech", "Monastir", "Med Tech Solutions"),
                    new Company("Solaris", "Tunis", "Solaris Co."),
                    new Company("Optima Consult", "Bizerte", "Optima Consulting"),
                    new Company("MegaTech", "Tunis", "MegaTech Ltd."),
                    new Company("Future Electronics", "Sfax", "Future Electronics Co.")
            );

            companyRepository.saveAll(companies);
            logger.info("Inserted dummy companies into the database.");
        } else {
            logger.info("Companies already exist, skipping insertion.");
        }
    }


    @Transactional
    private void createDummyClientsIfNeeded() {
        if (clientRepository.count() == 0) {
            // Creating a list of 20 unique dummy clients
            List<Client> clients = Arrays.asList(
                    new Client(null, "Marwen", "Jendouba", "marwen@gmail.com", "50339039", TypeClient.ENTREPRISE),
                    new Client(null, "Zied", "Bizert", "zied@gmail.com", "50339040", TypeClient.PARTICULIER),
                    new Client(null, "Anis", "Tunis", "anis@gmail.com", "50339041", TypeClient.ENTREPRISE),
                    new Client(null, "Bilel", "Nabeul", "bilel@gmail.com", "50339042", TypeClient.PARTICULIER),
                    new Client(null, "Sami", "Sousse", "sami@gmail.com", "50339043", TypeClient.ENTREPRISE),
                    new Client(null, "Amine", "Tunis", "amine@gmail.com", "50339044", TypeClient.PARTICULIER),
                    new Client(null, "Moez", "Mahdia", "moez@gmail.com", "50339045", TypeClient.ENTREPRISE),
                    new Client(null, "Salma", "Tunis", "salma@gmail.com", "50339046", TypeClient.PARTICULIER),
                    new Client(null, "Omar", "Monastir", "omar@gmail.com", "50339047", TypeClient.ENTREPRISE),
                    new Client(null, "Hassan", "Nabeul", "hassan@gmail.com", "50339048", TypeClient.PARTICULIER),
                    new Client(null, "Fatma", "Sfax", "fatma@gmail.com", "50339049", TypeClient.ENTREPRISE),
                    new Client(null, "Sofiane", "Sousse", "sofiane@gmail.com", "50339050", TypeClient.PARTICULIER),
                    new Client(null, "Noura", "Gabes", "noura@gmail.com", "50339051", TypeClient.ENTREPRISE),
                    new Client(null, "Rami", "Tunis", "rami@gmail.com", "50339052", TypeClient.PARTICULIER),
                    new Client(null, "Khaled", "Kairouan", "khaled@gmail.com", "50339053", TypeClient.ENTREPRISE),
                    new Client(null, "Mouna", "Sfax", "mouna@gmail.com", "50339054", TypeClient.PARTICULIER),
                    new Client(null, "Amira", "Tunis", "amira@gmail.com", "50339055", TypeClient.ENTREPRISE),
                    new Client(null, "Samiha", "Nabeul", "samiha@gmail.com", "50339056", TypeClient.PARTICULIER),
                    new Client(null, "Rania", "Mahdia", "rania@gmail.com", "50339057", TypeClient.ENTREPRISE),
                    new Client(null, "Youssef", "Gafsa", "youssef@gmail.com", "50339058", TypeClient.PARTICULIER)
            );

            // Save the dummy clients into the database
            clientRepository.saveAll(clients);
            logger.info("Inserted 20 dummy clients into the database.");
        } else {
            logger.info("Clients already exist, skipping insertion.");
        }
    }

    @Transactional
    private void createDummyBanksIfNeeded() {
        if (banqueRepository.count() == 0) {
            // Fetch companies to associate with banks
            List<Company> companies = companyRepository.findAll();

            List<Banque> banks = Arrays.asList(
                    new Banque(null,"Amen Bank", 100000.0, 95000.0), // Amen Bank
                    new Banque(null, "BIAT",200000.0, 180000.0), // BIAT
                    new Banque(null, "STB",150000.0, 145000.0), // STB
                    new Banque(null, "ATB",180000.0, 175000.0), // ATB
                    new Banque(null, "BH",250000.0, 240000.0), // BH
                    new Banque(null, "BNA",120000.0, 110000.0), // BNA
                    new Banque(null, "UIB",90000.0, 85000.0),  // UIB
                    new Banque(null, "Zitouna Bank",50000.0, 45000.0),  // Zitouna Bank
                    new Banque(null, "Arab Tunisian Bank",300000.0, 290000.0), // Arab Tunisian Bank
                    new Banque(null, "Banque Centrale de Tunisie",350000.0, 345000.0)  // Banque Centrale de Tunisie
            );

            banqueRepository.saveAll(banks);
            logger.info("Inserted dummy banks into the database.");
        } else {
            logger.info("Banks already exist, skipping insertion.");
        }
    }

    @Transactional
    private void createDummyProductsIfNeeded() {
        if (productRepository.count() == 0) {
            List<Product> products = Arrays.asList(
                    new Product(null, "Steel Beam", "High-strength steel beam", 120.50, 100, 15.0),
                    new Product(null, "Aluminum Sheet", "Lightweight aluminum sheet", 75.30, 200, 10.0),
                    new Product(null, "Copper Wire", "High-conductivity copper wire", 45.80, 150, 12.5),
                    new Product(null, "Iron Rod", "Durable iron rod", 90.00, 180, 8.0),
                    new Product(null, "Glass Panel", "Tempered glass panel", 65.25, 120, 5.5),
                    new Product(null, "PVC Pipe", "Flexible PVC pipe", 12.90, 300, 3.0),
                    new Product(null, "Ceramic Tile", "High-quality ceramic tile", 15.40, 400, 2.0),
                    new Product(null, "Concrete Block", "Heavy-duty concrete block", 2.50, 500, 1.0),
                    new Product(null, "Rubber Gasket", "Industrial-grade rubber gasket", 3.75, 1000, 0.5),
                    new Product(null, "Nails (Box)", "Box of 1000 nails", 5.00, 800, 1.5),
                    new Product(null, "Paint Bucket", "5-liter paint bucket", 18.90, 250, 6.0),
                    new Product(null, "Wood Plank", "Solid wood plank", 25.60, 150, 4.0),
                    new Product(null, "Steel Cable", "Heavy-duty steel cable", 85.00, 50, 9.0),
                    new Product(null, "Plastic Tubing", "Durable plastic tubing", 8.75, 400, 2.0),
                    new Product(null, "Adhesive Glue", "Industrial-strength glue", 7.25, 600, 1.5),
                    new Product(null, "Electrical Wire", "High-conductivity wire", 55.80, 300, 7.0),
                    new Product(null, "Sand (Bag)", "50kg bag of sand", 3.50, 700, 0.5),
                    new Product(null, "Gravel (Bag)", "25kg bag of gravel", 4.20, 600, 0.8),
                    new Product(null, "Brick", "High-quality red brick", 1.00, 1000, 0.2),
                    new Product(null, "Cement Bag", "50kg bag of cement", 6.75, 500, 2.0),
                    new Product(null, "PVC Fittings", "Pack of PVC fittings", 14.50, 200, 3.5),
                    new Product(null, "Metal Sheet", "Corrugated metal sheet", 95.00, 80, 10.0),
                    new Product(null, "Hinges (Pack)", "Pack of 10 metal hinges", 10.90, 300, 2.5),
                    new Product(null, "Screws (Box)", "Box of 500 screws", 8.50, 400, 1.5),
                    new Product(null, "Door Handle", "Stylish door handle", 12.30, 150, 3.0),
                    new Product(null, "Window Frame", "Aluminum window frame", 140.00, 70, 15.0),
                    new Product(null, "Insulation Foam", "Thermal insulation foam", 18.00, 220, 4.5),
                    new Product(null, "Plaster (Bag)", "25kg bag of plaster", 5.60, 400, 1.2),
                    new Product(null, "Pipe Clamp", "Metal pipe clamp", 2.75, 600, 0.5),
                    new Product(null, "Light Fixture", "Modern light fixture", 25.00, 120, 6.0),
                    new Product(null, "Switch Panel", "Electrical switch panel", 8.90, 300, 2.0),
                    new Product(null, "Drill Bit Set", "Set of 10 drill bits", 15.90, 250, 3.5),
                    new Product(null, "Safety Helmet", "Durable safety helmet", 20.50, 200, 4.0),
                    new Product(null, "Gloves (Pair)", "Industrial-grade gloves", 7.50, 400, 1.5),
                    new Product(null, "Face Mask", "Pack of 50 face masks", 12.00, 300, 2.5),
                    new Product(null, "Ear Protection", "Noise-cancelling ear protection", 15.00, 150, 3.0),
                    new Product(null, "Toolbox", "Multi-purpose toolbox", 45.00, 80, 8.0),
                    new Product(null, "Measuring Tape", "10-meter measuring tape", 9.00, 300, 2.0),
                    new Product(null, "Work Boots", "Steel-toe work boots", 50.00, 100, 9.0),
                    new Product(null, "Waterproof Jacket", "Heavy-duty jacket", 70.00, 75, 12.0),
                    new Product(null, "Hard Hat", "Safety-certified hard hat", 25.00, 150, 5.0),
                    new Product(null, "Welding Mask", "Auto-darkening welding mask", 65.00, 50, 10.0),
                    new Product(null, "Angle Grinder", "Powerful angle grinder", 120.00, 40, 20.0),
                    new Product(null, "Power Drill", "Cordless power drill", 85.00, 70, 15.0),
                    new Product(null, "Circular Saw", "High-precision circular saw", 140.00, 30, 25.0),
                    new Product(null, "Hammer", "Claw hammer", 15.00, 400, 2.5),
                    new Product(null, "Sledgehammer", "Heavy-duty sledgehammer", 50.00, 100, 7.5),
                    new Product(null, "Saw Blade", "Replacement saw blade", 25.00, 200, 5.0)
            );

            productRepository.saveAll(products);
            logger.info("Inserted dummy products into the database.");
        } else {
            logger.info("Products already exist, skipping insertion.");
        }
    }
    @Transactional
    public void createDummyFournisseursIfNeeded() {
        if (fournisseurRepository.count() == 0) {
            List<Fournisseur> fournisseurs = Arrays.asList(
                    new Fournisseur(null, "ABC Supplies", "123 Main St, Cityville", "contact@abcsupplies.com", "555-1234"),
                    new Fournisseur(null, "XYZ Enterprises", "456 Oak Rd, Villagetown", "sales@xyzenterprises.com", "555-5678"),
                    new Fournisseur(null, "Tech Innovators", "789 Maple Ave, Techcity", "info@techinnovators.com", "555-9876"),
                    new Fournisseur(null, "Green Goods", "101 Green St, Greenfield", "support@greengoods.com", "555-1122")
            );

            fournisseurRepository.saveAll(fournisseurs);
            System.out.println("Inserted dummy fournisseurs into the database.");
        } else {
            System.out.println("Fournisseurs already exist, skipping insertion.");
        }
    }
}