// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.usb.pocbot.cards;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.dialogs.ComponentDialog;
import com.microsoft.bot.dialogs.DialogTurnResult;
import com.microsoft.bot.dialogs.WaterfallDialog;
import com.microsoft.bot.dialogs.WaterfallStep;
import com.microsoft.bot.dialogs.WaterfallStepContext;
import com.microsoft.bot.dialogs.choices.FoundChoice;
import com.microsoft.bot.dialogs.prompts.ChoicePrompt;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.Attachment;
import com.microsoft.bot.schema.AttachmentLayoutTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.LoggerFactory;

public class MainDialog extends ComponentDialog {

    Choices choices = new Choices();

    public MainDialog() {
        super("MainDialog");

        WaterfallStep[] waterfallSteps = {
            this::choiceCardStep,
            this::certificationTopics,
            this::showCardStep
        };

        // Define the main dialog and its related components.
        addDialog(new ChoicePrompt("ChoicePrompt"));
        addDialog(new WaterfallDialog("WaterfallDialog", Arrays.asList(waterfallSteps)));

        // The initial child Dialog to run.
        setInitialDialogId("WaterfallDialog");
    }

    // 1. Prompts the user if the user is not in the middle of a dialog.
    // 2. Re-prompts the user when an invalid input is received.
    private CompletableFuture<DialogTurnResult> choiceCardStep(WaterfallStepContext stepContext) {
        LoggerFactory.getLogger(MainDialog.class).info("MainDialog.choiceCardStep");
        
        // Create the PromptOptions which contain the prompt and re-prompt messages.
        // PromptOptions also contains the list of choices available to the user.
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("Which Azure certifications are you interested in?"));
        promptOptions.setRetryPrompt(MessageFactory.text("That was not a valid choice, please click or type the card name"));
        promptOptions.setChoices(choices.getCertificationChoices());

        // Prompt the user with the configured PromptOptions.
        return stepContext.prompt("ChoicePrompt", promptOptions);
    }

    private CompletableFuture<DialogTurnResult> certificationTopics(WaterfallStepContext stepContext)
    {
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("Select/type the topic you are interested in"));
        promptOptions.setRetryPrompt(MessageFactory.text("That was not a valid choice, please click or type the card name"));

        switch (((FoundChoice) stepContext.getResult()).getValue()) {
            case "AZ-900":
                promptOptions.setChoices(choices.getAz900Topics());
        }

        return stepContext.prompt("ChoicePrompt", promptOptions);
    }

    // Send a Rich Card response to the user based on their choice.
    // This method is only called when a valid prompt response is parsed from the user's response to the ChoicePrompt.
    private CompletableFuture<DialogTurnResult> showCardStep(WaterfallStepContext stepContext) {
        LoggerFactory.getLogger(MainDialog.class).info("MainDialog.showCardStep");
        
        // Cards are sent as Attachments in the Bot Framework.
        // So we need to create a list of attachments for the reply activity.
        List<Attachment> attachments = new ArrayList<>();
        
        // Reply to the activity we received with an activity.
        Activity reply = MessageFactory.attachment(attachments);
        String userChoice = ((FoundChoice) stepContext.getResult()).getValue();
        reply.getAttachments().add(Cards.getHeroCard(userChoice).toAttachment());

        // Send the card(s) to the user as an attachment to the activity
        return stepContext.getContext().sendActivity(reply)
            .thenCompose(resourceResponse -> stepContext.getContext().sendActivity(
                // Give the user instructions about what to do next
                MessageFactory.text("Type anything to see another card.")
            ))
            .thenCompose(resourceResponse -> stepContext.endDialog());
    }
}
