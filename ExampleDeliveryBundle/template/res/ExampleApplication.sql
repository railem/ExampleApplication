CREATE  TABLE `T_Users` (`userId` INT NOT NULL auto_increment,`username` VARCHAR(45) NOT NULL ,`email` VARCHAR(45) NOT NULL ,PRIMARY KEY (`userId`) ) DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci;
CREATE  TABLE `T_Teams` (`teamId` INT NOT NULL auto_increment,`name` VARCHAR(45) NOT NULL ,PRIMARY KEY (`teamId`) )DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci;
CREATE  TABLE `T_User_Team` (`userId` INT NOT NULL ,`teamId` INT NOT NULL ) DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci;
