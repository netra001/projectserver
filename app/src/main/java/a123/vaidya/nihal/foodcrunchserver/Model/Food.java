package a123.vaidya.nihal.foodcrunchserver.Model;


public class Food {
<<<<<<< HEAD
<<<<<<< HEAD
    private String Name,Image,Description,Price,Discount,MenuId;

    public Food() {
    }

=======
=======
>>>>>>> old2/master
    private String FoodId,Name,Image,Description,Price,Discount,MenuId,Email,Video,Recepixes,rateValue;
    private Double Quantity;
    public Food() {
    }

    public Food(String foodId,String name, String image, String description, String price, String discount,
                String menuId, String email, String video, String recepixes,String rateValue,
                Double quantity
    ) {
        FoodId = foodId;
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Discount = discount;
        MenuId = menuId;
        Email = email;
        Video = video;
        Recepixes = recepixes;
        rateValue = rateValue;
        Quantity = quantity;

    }

<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> old2/master
    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> old2/master
    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master
    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public Food(String name, String image, String description, String price, String discount, String menuId) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Discount = discount;
        MenuId = menuId;

=======
=======
>>>>>>> old2/master
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getRecepixes() {
        return Recepixes;
    }

    public void setRecepixes(String recepixes) {
        Recepixes = recepixes;
<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master
    }
}
