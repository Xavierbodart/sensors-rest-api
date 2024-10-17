CREATE TABLE signals (
    node_id VARCHAR(52) PRIMARY KEY,
    sampling_interval INT,
    deadband_type VARCHAR(50),
    deadband_value INT,
    is_active BOOLEAN NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modification_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE keywords (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE signal_keywords (
    signal_node_id VARCHAR(52) NOT NULL,
    keyword_id INT NOT NULL,
    PRIMARY KEY (signal_node_id, keyword_id),
    FOREIGN KEY (signal_node_id) REFERENCES signals(node_id) ON DELETE CASCADE,
    FOREIGN KEY (keyword_id) REFERENCES keywords(id) ON DELETE CASCADE
);

INSERT INTO keywords (name, description) VALUES
('PS', 'Power Supply'),
('IBL', 'Inclined Beam Line'),
('TSS', 'Therapy Safety System'),
('P1', 'Proteus One'),
('DS', 'Double Scattering');
commit;
