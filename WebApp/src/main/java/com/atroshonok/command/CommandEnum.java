package com.atroshonok.command;

import com.atroshonok.command.admin.AddNewProductCommand;
import com.atroshonok.command.admin.BlackListCommand;
import com.atroshonok.command.admin.EditProductCommand;
import com.atroshonok.command.admin.SaveEditedProductCommand;
import com.atroshonok.command.admin.SaveNewProductCommand;
import com.atroshonok.command.admin.ShowAllProductsCommand;
import com.atroshonok.command.admin.ShowAllUsersCommand;
import com.atroshonok.command.client.AddToCartCommand;
import com.atroshonok.command.client.OrderCommand;
import com.atroshonok.command.client.RemoveFromCartCommand;
import com.atroshonok.command.client.ShowCartCommand;
import com.atroshonok.command.client.ShowProductsCommand;
import com.atroshonok.command.client.ShowUserOrdersCommand;

public enum CommandEnum {
    LOGIN {
	{
	    this.command = new LoginCommand();
	}
    },
    LOGOUT {
	{
	    this.command = new LogoutCommand();
	}
    },
    REGISTRATION {
	{
	    this.command = new RegistrCommand();
	}
    },
    SHOWPRODUCTS {
	{
	    this.command = new ShowProductsCommand();
	}
    },
    GETREGISTRFORM {
	{
	    this.command = new GetRegistrFormCommand();
	}
    },
    GETMAINPAGE {
	{
	    this.command = new GetMainPageCommand();
	}
    },
    SHOWCART {
	{
	    this.command = new ShowCartCommand();
	}
    },
    ADDTOCART {
	{
	    this.command = new AddToCartCommand();
	}
    },
    REMOVEFROMCART {
	{
	    this.command = new RemoveFromCartCommand();
	}
    },
    ORDER {
	{
	    this.command = new OrderCommand();
	}
    },
    SHOWUSERORDERS {
	{
	    this.command = new ShowUserOrdersCommand();
	}
    },
    SHOWALLPRODUCTS {
	{
	    this.command = new ShowAllProductsCommand();
	}
    },
    EDITPRODUCT {
	{
	    this.command = new EditProductCommand();
	}
    },
    SAVEEDITEDPRODUCT {
	{
	    this.command = new SaveEditedProductCommand();
	}
    },
    SHOWALLUSERS {
	{
	    this.command = new ShowAllUsersCommand();
	}
    },
    BLACKLIST {
	{
	    this.command = new BlackListCommand();
	}
    },
    ADDNEWPRODUCT {
	{
	    this.command = new AddNewProductCommand();
	}
    },
    SAVEPRODUCT {
	{
	    this.command = new SaveNewProductCommand();
	}
    },
    SETLANGUAGE {
	{
	    this.command = new SetLanguageCommand();
	}
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
	return command;
    }
}