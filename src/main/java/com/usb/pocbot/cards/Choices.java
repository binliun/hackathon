package com.usb.pocbot.cards;

import com.microsoft.bot.dialogs.choices.Choice;

import java.util.Arrays;
import java.util.List;

public class Choices {

    Choices() {
    }
    public List<Choice> getCertificationChoices() {
        return Arrays.asList(
                new Choice("AZ-900", "az900", "az"),
                new Choice("DP-900", "dp900", "dp"),
                new Choice("AI-900", "ai900", "ai")
        );
    }

    public List<Choice> getAz900Topics() {
        return Arrays.asList(
                new Choice("Cloud Models", "types of cloud models", "different cloud models"),
                new Choice("Cloud Service Types", "explain cloud types", "different cloud types")
        );
    }
}
