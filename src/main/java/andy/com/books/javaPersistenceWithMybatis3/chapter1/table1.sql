CREATE TABLE `STUDENTS` (
  `stud_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `dob` date DEFAULT NULL,
  PRIMARY KEY (`stud_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `STUDENTS` (`stud_id`, `name`, `email`, `dob`)
VALUES
	(1, 'Student1', 'student1@gmail.com', '1983-06-25'),
	(2, 'Student2', 'student2@gmail.com', '1983-06-25');
