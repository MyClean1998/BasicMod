package ninetailsmod.powers;

import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ninetailsmod.BasicMod;

public class GracePower extends BasePower  {

    public static final String ID = BasicMod.makeID(GracePower.class.getSimpleName());

    public GracePower(AbstractCreature owner) {
        super(ID, PowerType.BUFF, false, owner, -1);
    }

    private boolean hasPlayedAttack = false;

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        this.hasPlayedAttack = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.hasPlayedAttack = true;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !this.hasPlayedAttack && !AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic("Runic Pyramid") &&
            !AbstractDungeon.player.hasPower("Equilibrium")) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!c.isEthereal)
                    c.retain = true;
            }
        }
    }
}
