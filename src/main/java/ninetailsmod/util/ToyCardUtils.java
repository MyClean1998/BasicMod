package ninetailsmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import ninetailsmod.cards.BaseCard;

import java.util.ArrayList;

public class ToyCardUtils {
    public static AbstractCard getToyCard() {
        ArrayList<AbstractCard> cards = CardLibrary.getAllCards();
        CardGroup toyCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : cards) {
            if (c.hasTag(BaseCard.CustomTags.TOY)) {
                toyCards.addToBottom(c);
            }
        }
        return toyCards.getRandomCard(AbstractDungeon.cardRandomRng);
    }
}
