/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increament(View view) {
        if (quantity >= 100){
            Toast.makeText(this, "you can't have more than 100 Coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            quantity = quantity + 1;
        }

        displayQuantity( quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decreament(View view) {
        if (quantity <= 1){
            Toast.makeText(this, "you can't have less than 1 Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox choclateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = choclateCheckbox.isChecked();

        EditText userNameEditText = (EditText) findViewById(R.id.name_field);
        String userName = userNameEditText.getText().toString();


        int price = calculatePrice(quantity, hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream,hasChocolate ,userName );

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for "+ userName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);



        }


    }


    /*
    *create summary of the order
    * @param price of the order
    * @return text summary
    */

    private String createOrderSummary( int priceOfOrder, boolean hasWhippedCream, boolean hasChocolate, String userName){
        String priceMassege = getString(R.string.order_summary_name, userName);
        priceMassege = priceMassege + "\n" + getString(R.string.order_summary_whipped_cream,hasWhippedCream);
        priceMassege = priceMassege + "\n" + getString(R.string.order_summary_chocolate,hasChocolate);
        priceMassege = priceMassege + "\n" + getString(R.string.order_summary_quantity,quantity);
        priceMassege += "\n" + getString(R.string.order_summary_price,priceOfOrder);
        priceMassege = priceMassege + "\n" + getString(R.string.thank_you);
        return priceMassege;


    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice +=1;
        }
        if (hasChocolate){
            basePrice+=2;
        }
        int price = quantity * basePrice;
        return price ;
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}