package com.example.youngyeehomies.hssapp.Control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.youngyeehomies.hssapp.R;

/*
** This class is used to bring about general alerts when required
 */

public class AlertDialogManager {
    /**
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title and Message
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        if(status == null) {
            alertDialog.setIcon(R.drawable.warning);
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        else if (status) {
            alertDialog.setIcon(R.drawable.success);
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        else {
            alertDialog.setIcon(R.drawable.failed);
            alertDialog.setNegativeButton("Close",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }

        alertDialog.setCancelable(false);

        AlertDialog alert = alertDialog.create();

        alert.show();
    }


}
