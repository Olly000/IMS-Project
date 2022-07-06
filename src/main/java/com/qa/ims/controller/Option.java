package com.qa.ims.controller;

import com.qa.ims.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum Option {

    ADD("Add an item to the order"),

    REMOVE("Remove an item from the order");

    public static final Logger LOGGER = LogManager.getLogger();
    private String description;

    Option(String description) {this.description = description; }

    public String getDescription() {
        return description;
    }

    public static void printOptions() {
        for (Option option : Option.values()) {
            LOGGER.info(option.getDescription());
        }
    }

    public static Option getOption(Utils utils) {
        Option option = null;
        do {
            try {
                option = Option.valueOf(utils.getString().toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.error("Invalid selection please try again");
            }
        } while (option == null);
        return option;
    }



    public void setDescription(String description) {
        this.description = description;
    }
}

