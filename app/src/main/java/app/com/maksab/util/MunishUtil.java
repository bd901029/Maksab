package app.com.maksab.util;

/**
 * Created by RWS-DESIGNER on 5/24/2018.
 */

public class MunishUtil {
    /*1
   978-1-4673-6540-6/15/$31.00 ©2015 IEEE
  Touch Based Digital Ordering System on Android using GSM and Bluetooth for Restaurants

  Bhaskar Kumar Mishra School of Computing Sciences and Engineering VIT University Chennai, Tamil Nadu, India bhaskar.kumar2011@vit.ac.in
  Bhawani Singh Choudhary School of Computing Sciences and Engineering VIT University  Chennai, Tamil Nadu, India bhawanisingh.choudhary2011@vit.ac.in
  Tanmay Bakshi School of Electronics Engineering VIT University Chennai, Tamil Nadu, India tanmay.bakshi2011@vit.ac.in

  Abstract— Technology has entered almost every field in our life, but still its effect is not yet that evident in the food industry,
  especially the food serving outlets including restaurants, hotels. Even today, most of the restaurants in India follow the traditional
  pen and paper method to take orders from customers, which wastes a lot of time of both, the customer and the restaurant.
  This work aims to substitute the traditional pen and paper method by automating the food-ordering process in restaurant and thus improving
  the dining experience of the customer. This paper proposes an automated system that uses wireless communication, a centralized database, and an a
  ndroid application to place the order without even waiting for a waiter.
  The android application installed in the touch screen device, fitted at the table, contains all the menu details with pictures of each item.
  The ordered details are wirelessly sent to the chef and the cashier. The manager has his own android application that is used to update
  the menu that updates the central database, view and manage table wise customers’ orders, and receive feedbacks from the customer.
  This system improves efficiency and accuracy for restaurants by saving time, eliminating human errors, getting customers feedback.
  As the system is automated, it becomes economical even from restaurants point of view, as it reduces manpower and it just requires
  one time investment in installing the devices at tables.

  Keywords— Digital food-ordering system, Wireless food- ordering system,
  Android application, Touch based food-ordering system, On site Automated food-ordering system.

  I.INTRODUCTION
  With rapid economic and technology development people’s living standard has improved.
  In almost every area, technology has changed the traditional ways of doing things and has made the life easier and more convenient.
  However, food industry still lags behind other industries in adopting new technology, especially automation in various processes.
  Even today many restaurants follow completely manual process of pen and paper in food ordering. In traditional pen and paper method,
  the waiter notes down the orders from customers, takes these orders to the kitchen, updates them in records, delivers the ordered items at
  the appropriate table and then makes the bill. This system is conventional and too sluggish. It requires more manpower and thus is prone to
  human errors. Apart from errors, it consumes a lot of time. It disturbs the serenity of a eating and hangout place and results in chaos. So,
  this system often leads to dissatisfaction among the customers, as sometimes time taken by waiter for taking order is very long.

  In recent past, some systems like PDA based system, KIOSK technology, and multi touchable restaurants management systems were developed to
  automate the food ordering process. However, the results of these systems were not up to the expectation. They provided uninformative and
  unattractive menu details, and they were also costly to adopt.
  To overcome these limitations of the system, a touch based digital ordering using an android application is proposed to automate the
  food-ordering process. This system provides an attractive user interface through the android application.
  It also provides the images of all the menu items along with their prices so that it becomes easier for the customer to order.
  It has the facility to give customized personal message to the manager for the food order. It allows the customer to give feedback to the manager.
  It also allows the customer to call a waiter, using the android application, for help.


  II. RELATED WORK
  A.Pen and paper based traditional system
  This is the simplest and the most widely used system even today.
  In this system, every time a customer   enters the restaurant and occupies his table, a waiter comes at his table and presents him a menu card,
  with the record of food-items.
  Waiter then waits with a notepad and a pen to take down the customer’s order.
  The waiter then notes down the order of the customer in his notepad and the record is stored in the paper.

Finally, the record is given to the chef in the kitchen.
Although the food ordering process in this system is very easy, there are many drawbacks in this system.

This system requires storage of lots of paper which does paper wastage.
Since, this is a complete manual process, there is a high chance of human errors due to many reasons such as while taking the orders the waiter
may miss some items to add, the other human error is that the paper could get lost or could get damaged by fire, or by water due to mishandling.
The menu cards containing the list of items are also in a hard paper.
So, if the manager wants to update the menu lists or the price of items, then it would require him to change the menu details in each and
every menu cards present in the restaurant.
It is quite evidently cumbersome task to change details in every card.
So, eventually it leads to replacement of all the menu cards with the new one, which would lead to a great wastage of papers. Many times menu
cards might require very minute changes for which it is not at all convenient to replace all the menu cards with the new ones.
In simple words, these paper based menu cards lacks dynamicity.
It even becomes a very difficult task for a restaurant manager to analyze the order lists of customers to determine the best-selling food items
and the peak hour of restaurants to increase its market. There is also no proper tracking system in this pen and paper based ordering process.
Waiter has to constantly check with the chefs to determine which orders are ready.
They also need to check frequently which tables are empty, which are reserved and which tables need clearance.
All these things require a large manpower that is costly even from restaurants point of view.
It comes up with wastage of time, money and paper.
So, there is a need among restaurants to change this system in order to stand out in this competitive food industry.

B. Personal Digital Assistant (PDAs)
There were many improvements done in the food-ordering process, one among them was PDA based system.
PDAs are small handheld devices that make them easy to handle and portable.
PDA based system is a wireless system. Some examples of PDA based system are WOS, I-Menu, and FIWOS.
These wireless food-ordering systems enable customers or waiters to key in order using mobile devices, called, Personal Digital Assistant.
When a customer or waiter completes the ordering process, the order details are sent to the server from the PDA.
The waiters collect the PDAs used by a customer so that other customers can use it.

These PDA based system may be a better approach towards automated food-ordering system over traditional pen and paper based system but it has
many limitations too. PDA based system may increase the restaurant expenditures during the peak hours, as during peak hours the restaurant
require large numbers of PDAs to serve every customer.
There is not any way to get a real time feedback from the customers, in PDA based system. Technical details are required for the restaurant
manager to update or modify the menu list.
The User Interface of the PDA based system is not so attractive.
It consists of only textual information. There are no images of the food items.
So, it contains unattractive and uninformative details about the menu list.

C. KIOSK based system
KIOSK is a screen that contains the complete menu list.
It is more advanced than previous two systems.
It not only contains the textual information about the menu items but also contains images of every food-item along with their prices.

KIOSK screen in installed at the restaurants mostly near cash counter. Whenever, a customer visits a restaurant he has to check the menu list at
the KIOSK screen.
 He browses through the list and selects his items and completes the order.
 Payment is also done through the KIOSK screen by a suitable payment option.
 He then receives his order number.
 His ordered list along with the order number is sent to the chef in kitchen. After the order is ready the order number is announced or displayed
 on the screen at the cash counter. The customer has to come and receive his order.

  This system, although, is more advanced than the previous two systems has limitations too.
  If the restaurant is in its peak hours and is crowded with many customers then this system ends up in forming a big queue for the KIOSK.
  It leads to frustrations and inconvenience among the customers. Eventually, it becomes even a worse option than other two systems in that case.
  This is the greatest disadvantage of this system. However, this system proves to be good with restaurants having moderate number customers or
  the restaurants that do quick orders such as, fast food.

D. Computer based food ordering
This system is similar to KIOSK technology system.
In this system, when customer enters the restaurant, he has to orally tell the orders to the cashier and make the payment.
The cashier makes a bill in his name with order number written in the bill.
After placing the order the customer has to wait in his table. The cashier sends the order to the chef in the kitchen. When the order is completed,
the waiter comes and serves the order to the customer at his table.
This system being almost similar to the KIOSK based system has same limitations as that.
If a large number of customers come in the restaurant then it would become difficult for the cashier to take the orders from each customer
and send to the kitchen.


III. PROPOSED WORK
Though many steps were taken earlier to automate the food ordering system, they have not yet become popular enough to be
adopted by restaurants all over the world.
They have many limitations like, they are not customer friendly, sometimes these systems may take a lot of time for taking the order itself,
they don’t have proper real time feedback system between customers and restaurants managers, and they are not cost effective.
To overcome the limitations of these systems, we propose the design of touch based digital ordering systems for restaurants using Android,
Bluetooth and GSM. Android has become extremely popular in our daily life.
It has revolutionized the use of mobile technology to support automation of routine tasks in wireless environment.

Using android device, through this system, we aim to make a system that is convenient to use from both the customers, and the restaurants point
of views and automates the food ordering process in a cost effective way.
It makes the dining experience of the customer much more convenient than before.
It also provides a functionality to get real time feedback from customers.
It allows managers to announce various offers or special food items instantly through the app. The system becomes easy to use for managers also.
Since the menu lists and transactions of food items are stored in a centralized database, it becomes very easy for managers to analyze the
best-selling items by getting those values from the database.
This system is cost effective, as it only requires one time investment in installing android device at each table.
Since this automates the food-ordering process in a restaurant, it, therefore, reduces the manpower that saves a lot of money of the restaurant.


A. System Architecture
This digital ordering system uses two different android apps:
1) Customers app
2) Restaurant app. The customers’ android app is made exclusively for customers and is identified by the table number on which the app is installed.
The order sent from a particular customer app is sent along with the table number.
The second type of app is the restaurant app. It has three different types of users – manager, cashier, and the chef. The app has different
functionality as per the user type. Manager has administrative access on the app and has complete control over the entire system and
the centralized database. The cashier can view the ordered items and their prices and then collect the money from the respective customer.
The chef can mark the order when it is ready and then manager informs the waiter to serve the order.

Fig 1: System Architecture This system consists of four different areas:
1) customers dining area
2) manager area
3) kitchen area
4) Cash Counter.

1) Customers Dining Area -
The customers dining area contains android touch device installed on every table. These devices have
an app already installed in them and they will be always open.
The app has an attractive, simple and user- friendly interface.
It contains the list of all the food items available at the restaurant with their images to make it convenient for the customers to select the items.
It also has a feature to call a waiter, at any time, for help by just a click. Many customers have choices of level of some ingredients used
in making the food items; for example, some customers may like spicy items while others may want plain items.
So, using the app, customers can also write a brief description about their preference towards the food-items.

2)Manager Area –
There is a separate android application for manager.
Manager has access to order details of each and every tables of the restaurant. From his app he can identify all the empty tables and the reserved
tables too, so that he doesn’t have to send waiters to observe the entire empty and the reserved tables manually.
In fact, he can also send any personal message to any customer at a particular table via the app. He can edit or update the menu list through the
app after which the menu list gets updated in the centralized database and is then reflected in the customers’ android app.
The app also allows the manager to announce any special offers on a particular day.

3)Kitchen Area –
The kitchen area contains a LCD screen in front of the chef. The screen displays the ordered items along with the table number at
the bottom. When a customer places the order, it is sent via wireless GSM to the server and is then stored in the database. The chef has the
restaurant app that fetches the data from the database and displays on his device. Then the data is sent wirelessly through the Bluetooth and
is displayed on the LCD screen.
The chef then prepares the order and when the order is ready, he marks it completed on the app. The order is then removed from the LCD screen
and a new order is displayed.
The manager then gets notified of the completed order through his app and asks the waiter to serve on the particular table.

4)Cash Counter Area –
The cash counter area has ‘read’ access to the orders which are continuously placed on various tables.
The job of the application is to collect every order table wise and prepare bills at the end of the meal for the customer.


B. System Design
The manager/administrator logs into the system, through the restaurant app, and updates the available menu items along with
their prices.
He also advertises the various offers of the day. He has complete control over the entire system. He has a list of all empty and the reserved tables.
On arrival a customer is assigned one of the empty tables from where he can access his own application.
He then selects the items through the app and places the order along with the description for customization of dishes.
The order is the sent wirelessly via GSM connection to the chef in the kitchen, the manager and the cashier.
The manager can update the status of the order. The customer can also view their order status in their app.
When the order is ready, the chef notifies the manager through the app about the completed order.
The manager then asks the waiter to serve the order at appropriate table.
After having the food, the customer gives the feedback through the app and then he arrives at the cash counter and pays the bill to the cashier.

Fig 2: System Design and course of actions The components used to make this system are:

 Android Operating System - Android is a Linux-based
operating system designed primarily for touchscreen mobile devices such as smartphones and tablet computers.
It is an open source operating system and supports a number of devices.
It has a very large user base. Even from developer point of view it is cheaper than other popular mobile operating systems like IOS.

 Touchscreen - Touch screens are a clear sheet of plastic with tiny sensors that detect pressure from either a fingertip or a pointing device.
When these sensors are pressed, they perform the functions of clicking and dragging.
A software utility needs to be installed on the computer hard drive to further customize the different settings.
Touch screens are great for the cause and effect and software applications that require direct select.
It has now become popular among mobile devices and the tablets. It should have properties like high durability,
less susceptible to contaminants, scratch resistance. In addition it should be transparent enough to visualize menu, it should have better
response time and stability.

 LCD – It is Liquid Crystal Display. There are different types of LCD in the market. In the proposed system,
LCD is used to display the orders along with the table number to the chef; so, it should be big enough to accommodate the textual information
of the order on the screen.

 Arduino - Arduino is a tool for making computers that can sense and control more of the physical world than
the desktop computer.
It's an open-source physical computing platform based on a simple microcontroller board, and a development environment for writing software for the
board. It is used in the system to connect the chef’s android device to the LCD screen via Bluetooth. 

 GSM – GSM is used as a means of wireless communication in the proposed system.
It is preferable over other wireless communication like Zigbee because it has less cost, improved quality performance, high range, and reliability.
GSM has range of 1000+ meters.
GSM is also a better choice over RF communication because when more than 3RF modules serve in the same area, none of them works, whereas,
this is not the case with GSM module.


IV. CONCLUSION
In this paper, we proposed the automated food ordering system for the restaurant.
The system is compared to earlier food ordering traditional methods such as, traditional pen and paper methods, KIOSK technology, and PDAs
based system etc. We have discussed advantages of the proposed system over those earlier methods.
The differentiating factor for the proposed methodology is its adjustable efficiency which comes from the technology it uses.
Since the system relies on GSM which has got a bandwidth of 900MHz and the communication format is elementary which doesn’t require much channel
bandwidth, the number of channels increases substantially and hence the efficiency. If the requirement is huge, further channels can be added using
CDMA technology to the existing ones and the same available bandwidth can be used to serve even more number of tables at same time.

This system removes the manual process of food ordering and thus reduces the number of restaurant staffs saving cost of labor to a great extent.
Implementing this system only requires one time investment in installing the necessary devices in the restaurant.
It saves human errors to a great extent as this whole process is automated and does not involve manual pen and paper methods.
It also saves time by making the food ordering process independent of restaurant staffs. Since this system makes the food ordering fast,
it prevents forming of a long queue in front of the counter. Thus, this is an advanced and cost effective system for food ordering.
It also simplifies the complex static method of editing the menu lists by making the editing process dynamic through the app.
The manager can use his app and database transactions information to determine the best-selling food items, peak hours of restaurants and
hence can increase the productivity and revenue of his restaurant. The customers can also give real time feedback through the app that
helps manager to get the dining experience of the customers and remove the problems faced by them. This system provides customers a convenient,
user-friendly and attractive user interfaces with images of every food item, through which he can easily place an order without any difficulties.
Customers can also call the waiter or any restaurant staff for help by just a single click on the app.

Thus, the proposed system brings advancement in the field of food industry by automating the system through mobile and wireless technology.
It has the potential to attract customers and changing their dining experience in a better and efficient way.

The idea can be extended to add a functionality of accepting payments through credit cards, debit cards or through mobile wallet,
in the customer’s app. It can also be extended to accept the food order from outside the restaurant by making an application that can be
used by the customers to place orders from their homes.

REFERENCES
[1] Resham Shinde, Priyanka Thakare, Neha Dhomne, Sushmita Sarkar, “Design and Implementation of Digital dining in Restaurants using Android”, in International Journal of Advance Research in Computer Science and Management Studies, Volume 2, Issue 1, January 2014.
[2] Shweta Shashikant Tanpure, Priyanka R. Shidankar, Madhura M. Joshi, “Automated Food Ordering System with Real-Time Customer Feedback”, in International Journal of Advanced Research in Computer Science and Software Engineering, Volume 3, Issue 2, February 2013.
[3] Kirti Bhandge, Tejas Shinde, Dheeraj Ingale, Neeraj Solanki, Reshma Totare, “A Proposed System for Touchpad Based Food Ordering System Using Android Application”, in International Journal of Advanced Research in Computer Science & Technology (IJARCST 2015), Vol. 3, Issue 1 (Jan. - Mar. 2015).
[4] Sushmita Sarkar, Resham Shinde, Priyanka Thakare, Neha Dhomne, Ketki Bhakare, “Integration of Touch Technology in Restaurants using Android”,in IJCSMC, Vol. 3, Issue. 2, February 2014, pg.721 – 728.  [5] Varsha Chavan, Priya Jadhav, Snehal Korade and Priyanka Teli, “Implementing Customizable Online Food Ordering System Using Web Based Application”, in International Journal of Innovative Science, Engineering & Technology, Vol. 2 Issue 4, April 2015.  [6] Vikas Mullemwar, Vaibhav Virdande, Madhura Bannore, Ashwini Awari, Raviprakash Shriwas, "Electronic Menu card For Restaurants”, in International Journal of Research in Engineering and Technology.  [7] Ashutosh Bhargave, Niranjan Jadhav, Apurva Joshi, Prachi Oke, Prof. Mr. S. R Lahane, “Digital Ordering System for Restaurant Using Android”, in International Journal of Scientific and Research Publications, Volume 3, Issue 4, April 2013.  [8] Nibras Othman Abdul Wahid (2014). "Improve the Performance of the Work of the Restaurant Using PC Touch Screen", in Computer Science Systems Biology. [9] Khairunnisa K., Ayob J., Mohd. Helmy A. Wahab, M. Erdi Ayob, M. Izwan Ayob, M. Afif Ayob, “The Application of Wireless Food Ordering System”, in MASAUM Journal of Computing, Volume 1 Issue 2, September
5*/
}
