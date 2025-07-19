package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardAllAttackAction extends AbstractGameAction {

    private float startingDuration = Settings.ACTION_DUR_FAST;

    public DiscardAllAttackAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DISCARD;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type == AbstractCard.CardType.ATTACK)
                    addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.hand));
            }
            this.isDone = true;
        }
    }
}
