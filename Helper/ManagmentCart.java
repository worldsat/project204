package com.uilover.project2041.Helper;

import android.content.Context;
import android.widget.Toast;

import com.uilover.project2041.Model.ItemsModel;

import java.util.ArrayList;

public class ManagmentCart {
    private TinyDB tinyDB;
    private Context context;

    public ManagmentCart(Context context) {
        tinyDB = new TinyDB(context);
        this.context = context;
    }

    public void insertItems(ItemsModel item) {
        ArrayList<ItemsModel> listFood = getListCart();
        boolean existAlready = false;
        int index = -1;

        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                index = i;
                break;
            }
        }

        if (existAlready) {
            listFood.get(index).setNumberInCart(item.getNumberInCart());
        } else {
            listFood.add(item);
        }
        tinyDB.putListObject("CartList", listFood);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<ItemsModel> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusItem(ArrayList<ItemsModel> listItems, int position, ChangeNumberItemsListener listener) {
        if (listItems.get(position).getNumberInCart() == 1) {
            listItems.remove(position);
        } else {
            listItems.get(position).setNumberInCart(listItems.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList", listItems);
        listener.onChanged();
    }

    public void plusItem(ArrayList<ItemsModel> listItems, int position, ChangeNumberItemsListener listener) {
        listItems.get(position).setNumberInCart(listItems.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listItems);
        listener.onChanged();
    }

    public double getTotalFee() {
        ArrayList<ItemsModel> listFood = getListCart();
        double fee = 0.0;
        for (ItemsModel item : listFood) {
            fee += item.getPrice() * item.getNumberInCart();
        }
        return fee;
    }
}
