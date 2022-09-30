// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.usb.pocbot.cards;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.ConversationState;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.builder.UserState;
import com.microsoft.bot.dialogs.Dialog;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ChannelAccount;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.lang3.StringUtils;

// RichCardsBot prompts a user to select a Rich Card and then returns the card
// that matches the user's selection.
public class CertificationCardsBot extends DialogBot<Dialog> {

    public CertificationCardsBot(
        ConversationState withConversationState,
        UserState withUserState,
        Dialog withDialog
    ) {
        super(withConversationState, withUserState, withDialog);
    }

    @Override
    protected CompletableFuture<Void> onMembersAdded(
        List<ChannelAccount> membersAdded, TurnContext turnContext
    ) {
        return turnContext.getActivity().getMembersAdded().stream()
            .filter(member -> !StringUtils
                .equals(member.getId(), turnContext.getActivity().getRecipient().getId()))
            .map(channel -> {
                // Greet anyone that was not the target (recipient) of this message.
                // To learn more about Adaptive Cards, see https://aka.ms/msbot-adaptivecards for more details.
                Activity reply = MessageFactory.text("Hello!! This bot will help you search for different Azure certification."
                   + " Please type any message to continue!");

                return turnContext.sendActivity(reply);
            })
            .collect(CompletableFutures.toFutureList())
            .thenApply(resourceResponse -> null);
    }
}
