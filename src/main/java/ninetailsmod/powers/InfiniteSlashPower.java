package ninetailsmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import ninetailsmod.BasicMod;
import ninetailsmod.cards.attacks.FrenziedClaw;

public class InfiniteSlashPower extends BasePower  {

    public static final String ID = BasicMod.makeID(InfiniteSlashPower.class.getSimpleName());

    public InfiniteSlashPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (FrenziedClaw.isClaw(card)) {
            addToBot(new DrawCardAction(amount));
        }
    }
}
