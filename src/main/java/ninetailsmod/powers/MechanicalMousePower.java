package ninetailsmod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import ninetailsmod.BasicMod;
import ninetailsmod.cards.BaseCard;

import java.util.ArrayList;

public class MechanicalMousePower extends BasePower {
    public static final String ID = BasicMod.makeID(MechanicalMousePower.class.getSimpleName());

    public MechanicalMousePower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (int i = 0; i < this.amount; i++)
                addToBot(new MakeTempCardInHandAction(getToyCard().makeCopy(), 1, false));
        }
    }

    private static AbstractCard getToyCard() {
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
