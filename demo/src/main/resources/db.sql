create database spring_boot_demo;
CREATE USER 'spring_boot_demo'@'localhost' IDENTIFIED BY 'spring_boot_demo';
GRANT ALL PRIVILEGES ON spring_boot_demo.* TO 'spring_boot_demo'@'localhost';
CREATE TABLE `state_mst` (                 
             `state_id` bigint(20) NOT NULL,          
             `state_code` varchar(255) DEFAULT NULL,  
             `state_name` varchar(255) DEFAULT NULL,  
             PRIMARY KEY (`state_id`)                 
           ) ENGINE=MyISAM DEFAULT CHARSET=latin1