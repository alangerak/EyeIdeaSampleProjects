package ShoppingCart;

public class Validator {

    private float totalAmount;

    public Validator(){
        totalAmount = 0;
    }

    public void addAmount(float amount) {
        if(amount > 0){
            this.totalAmount += amount;
        }
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void success(){
        System.out.println("Paid Succesfully!");
    }
}
