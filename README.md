# Grab a Byte! 🍴

Hello! And welcome to **Grab a Byte!** — a Java ordering system. It welcomes all the customer with a smile, which they
later keep on their faces, as they leave.

## Features ⚙️️

- It’s **possible to order** lunch and/or drink.
- Lunch consists of a **main course and dessert**.
- Each meal and drink must have a **name and price**.
- There are **three cuisines** available to choose from (Polish, Mexican, Italian).
- When ordering a drink, the customer can additionally **ask for ice cubes or/and lemon**.

## How to Run the Project 🚀

### Prerequisites

The project requires the following to be installed on your system:

- Java 21
- Maven

### Running the Application

To run the application, open up your terminal and execute the following command:

```bash
mvn clean install exec:java
```

## Customization 🛠

- Edit [menu.csv](src/main/resources/menu.csv) to add or remove menu items.
- Add a cuisine to the [Cuisine enum](src/main/java/com/antypo/grababyte/menu/model/Cuisine.java) to offer it for the
  customers (meals of that cuisine still need to be added to the [menu.csv](src/main/resources/menu.csv) file).
- Change **fileName** in the [Menu class](src/main/java/com/antypo/grababyte/menu/Menu.java) to be able to rename
  the [menu.csv](src/main/resources/menu.csv) to the same new name.

## Upcoming features ⭐

- **Statistics by date** - as we store orders' dates, why should it go to waste?
- **Price modifications** - price should change along with ingredients (for the meal) or extras (for the drink). There
  should also be some discounts.
- **Display personalized items** - after an edit of ingredients or extras, these should be accounted for in the order
  summary.

## Contributing 🤝

I welcome all contributions, especially employment contracts. 😉