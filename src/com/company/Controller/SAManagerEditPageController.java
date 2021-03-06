package com.company.Controller;

import com.company.Main;

import com.company.Model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SAManagerEditPageController implements Initializable {


    @FXML
    Button cancelBTN;
    @FXML Button editBTN;
    @FXML
    TextField nameTF;
    @FXML TextField lastnameTF;
    @FXML TextField usernameTF;
    @FXML TextField passwordTF;
    @FXML TextField creditTF;
    @FXML TextField emailAddressTF;
    @FXML TextField phoneNumberTF;
    @FXML TextField idTF;
    @FXML
    Label warningLBL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Manager manager= Main.managers.get(SAManagerListPageController.editManagerIndex);

        nameTF.setText(manager.getName());
        lastnameTF.setText(manager.getLastname());
        idTF.setText(manager.getId());
        emailAddressTF.setText(manager.getEmailAdress());
        phoneNumberTF.setText(manager.getPhoneNumber());
        passwordTF.setText(manager.getPassword());
        usernameTF.setText(manager.getUsername());
        creditTF.setText(manager.getSalary());

        cancelBTN.setOnAction(e -> {
            ((Stage) cancelBTN.getScene().getWindow()).close();
            SAManagerListPageController.editManagerPageIsOpen=false;
        });

        editBTN.setOnAction(e -> {
            try {
                editBTNaction(e);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }

    private void editBTNaction(ActionEvent e) throws IOException {
        if (nameTF.getText().isEmpty() || lastnameTF.getText().isEmpty() || usernameTF.getText().isEmpty() || passwordTF.getText().isEmpty()
                || creditTF.getText().isEmpty() || emailAddressTF.getText().isEmpty() || idTF.getText().isEmpty())
        {
            //some fields arnt filled
            warningLBL.setText("Copmlete all the fields!!!");
        }
        else
        {
            if (!checkSpace())
            {
                //there is space in the fields
                warningLBL.setText("Remove any space in the fields!!!");
            }
            else
            {
                if (!checkFormat())
                {
                    //invalid format
                    warningLBL.setText("Use valid format in the fields!!!");
                }
                else if (!checkExistance())
                {
                    warningLBL.setText("There is already a user with this info!!!");
                }
                else
                {
                    //everything is ok
                    //lets edit
                    int index=SAManagerListPageController.editManagerIndex;

                    //edit the object
                    Main.managers.get(index).setSalary(creditTF.getText());
                    Main.managers.get(index).setName(nameTF.getText());
                    Main.managers.get(index).setLastname(lastnameTF.getText());
                    Main.managers.get(index).setId(idTF.getText());
                    Main.managers.get(index).setUsername(usernameTF.getText());
                    Main.managers.get(index).setPassword(passwordTF.getText());
                    Main.managers.get(index).setEmailAdress(emailAddressTF.getText());
                    Main.managers.get(index).setPhoneNumber(phoneNumberTF.getText());
                    //write in file
                    WriteReadFile<Manager> managerWriteReadFile=new WriteReadFile<>(Main.managers, "Managers.txt");
                    managerWriteReadFile.writeList();

                    //refresh the tableview
                    SAManagerListPageController.fakeManagersTV.getItems().clear();
                    for (int i=0 ; i< Main.managers.size() ; i++)
                    {
                        SAManagerListPageController.fakeManagersTV.getItems().add(Main.managers.get(i));
                    }
                    SAManagerListPageController.fakeManagersTV.refresh();


                    warningLBL.setText("");
                    ((Stage)editBTN.getScene().getWindow()).close();

                }
            }
        }
    }





    private boolean checkFormat()
    {
        //name:
        if (!(nameTF.getText().matches("[a-zA-Z]+")) || !(lastnameTF.getText().matches("[a-zA-Z]+")))
        {
            return false;
        }

        if (!(idTF.getText().matches("[0-9]+")))
        {
            return false;
        }

        if ( (phoneNumberTF.getText().length() !=11) || !(phoneNumberTF.getText().substring(0 , 2).equals("09")) || !(phoneNumberTF.getText().matches("[0-9]+")))
        {
            return false;
        }

        if (!(emailAddressTF.getText().matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")))
        {
            return false;
        }
        if (!(creditTF.getText().matches("[0-9]+")))
        {
            return false;
        }

        return true;

    }


    private boolean checkSpace()
    {
        //name:
        String check1=nameTF.getText();
        check1=check1.trim();
        String[] tempCheck1=check1.split(" ");
        if (tempCheck1.length>1)
        {
            return false;
        }


        //lastname
        String check2=lastnameTF.getText();
        check2=check2.trim();
        String[] tempCheck2=check2.split(" ");
        if (tempCheck2.length>1)
        {
            return false;
        }

        //usernsme
        String check3=usernameTF.getText();
        check3=check3.trim();
        String[] tempCheck3=check3.split(" ");
        if (tempCheck3.length>1)
        {
            return false;
        }

        //password
        String check4=passwordTF.getText();
        check4=check4.trim();
        String[] tempCheck4=check4.split(" ");
        if (tempCheck4.length>1)
        {
            return false;
        }

        //credit
        String check5=creditTF.getText();
        check5=check5.trim();
        String[] tempCheck5=check5.split(" ");
        if (tempCheck5.length>1)
        {
            return false;
        }

        //phone number
        String check6=phoneNumberTF.getText();
        check6=check6.trim();
        String[] tempCheck6=check6.split(" ");
        if (tempCheck6.length>1)
        {
            return false;
        }


        //email adress
        String check8=emailAddressTF.getText();
        check8=check8.trim();
        String[] tempCheck=check8.split(" ");
        if (tempCheck.length>1)
        {
            return false;
        }

        return true;

    }

    private boolean checkExistance()
    {
        //id:
        for (int i = 0; i< Main.employees.size() ; i++)
        {
            if (idTF.getText().equals(Main.employees.get(i).getId()) )
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (idTF.getText().equals(Main.passangers.get(i).getId()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (idTF.getText().equals(Main.managers.get(i).getId()) && i!=SAManagerListPageController.editManagerIndex)
            {
                return false;
            }
        }
        if (idTF.getText().equals(Main.superAdmin.getId()))
        {
            return false;
        }

        //username:
        for (int i=0 ; i<Main.employees.size() ; i++)
        {
            if (usernameTF.getText().equals(Main.employees.get(i).getUsername()) )
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (usernameTF.getText().equals(Main.passangers.get(i).getUsername()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (usernameTF.getText().equals(Main.managers.get(i).getUsername()) && i!=SAManagerListPageController.editManagerIndex)
            {
                return false;
            }
        }
        if (usernameTF.getText().equals(Main.superAdmin.getUsername()))
        {
            return false;
        }

        //email:
        for (int i=0 ; i<Main.employees.size() ; i++)
        {
            if (emailAddressTF.getText().toLowerCase().equals(Main.employees.get(i).getEmailAdress().toLowerCase()) )
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (emailAddressTF.getText().toLowerCase().equals(Main.passangers.get(i).getEmailAdress().toLowerCase()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (emailAddressTF.getText().toLowerCase().equals(Main.managers.get(i).getEmailAdress().toLowerCase())&& i!=SAManagerListPageController.editManagerIndex)
            {
                return false;
            }
        }
        if (emailAddressTF.getText().toLowerCase().equals(Main.superAdmin.getEmailAdress().toLowerCase()))
        {
            return false;
        }

        //phone nmbr:
        for (int i=0 ; i<Main.employees.size() ; i++)
        {
            if (phoneNumberTF.getText().equals(Main.employees.get(i).getPhoneNumber()) )
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (phoneNumberTF.getText().equals(Main.passangers.get(i).getPhoneNumber()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (phoneNumberTF.getText().equals(Main.managers.get(i).getPhoneNumber())&& i!=SAManagerListPageController.editManagerIndex)
            {
                return false;
            }
        }
        if (phoneNumberTF.getText().equals(Main.superAdmin.getPhoneNumber()))
        {
            return false;
        }

        return true;
    }
}
