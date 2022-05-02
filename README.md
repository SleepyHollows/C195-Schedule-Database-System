# C195-Schedule-Database-System
Student Name: Caleb Hathaway

Date: 04/23/2022

IDE: Intellij

IDE Version: 2021.3.3 (Community Edition)

JDK Version: openjdk-17 version 17.0.1

SDK version: SDK default 17

mysql connector Version: 8.0.28


A GUI application that interacts with a database to store appointments and customer data.

You are working for a software company that has been contracted to develop a GUI-based scheduling desktop application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England. The consulting organization has provided a MySQL database that the application must pull data from. The database is used for other systems, so its structure cannot be modified.

The organization outlined specific business requirements that must be met as part of the application. From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section.

Your company acquires Country and First-Level-Division data from a third party that is updated once per year. These tables are pre-populated with read-only data. Please use the attachment “Locale Codes for Region and Language” to review division data. Your company also supplies a list of contacts, which are pre-populated in the Contacts table; however, administrative functions such as adding users are beyond the scope of the application and done by your company’s IT support staff. Your application should be organized logically using one or more design patterns and generously commented using Javadoc so your code can be read and maintained by other programmers.

How to run: The project should work out of the box after making sure you have to proper JDK/SDK and mysql connector, just hit run, and you should be taken to the loginMenu. From the loginMenu you will be able to log in then you will be taken to the overviewMenu. The overviewMenu will list all customers and appointments as well as allow you to go logout, add customer, edit customer, delete customer, add appointment, edit appointment, delete appointment, and open reportsMenu. reports menu will just show you multiple types of reports matching a specific criteria.


The additional report I built is by customer, this allows for the company to view all upcoming appointments for a specific customer. The report I choose will help us better track what the customer is planning and needing help with.  