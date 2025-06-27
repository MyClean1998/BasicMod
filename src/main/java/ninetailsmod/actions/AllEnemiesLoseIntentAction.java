package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AllEnemiesLoseIntentAction extends AbstractGameAction {

    private float startingDuration = Settings.ACTION_DUR_FAST;

    public AllEnemiesLoseIntentAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                mo.setMove((byte)-1, AbstractMonster.Intent.UNKNOWN);
                mo.createIntent();
                addToTop(new TextAboveCreatureAction(mo, TextAboveCreatureAction.TextType.INTERRUPTED));
                addToTop(new SetMoveAction(mo, (byte)-1, AbstractMonster.Intent.UNKNOWN));
//                mo.intent = AbstractMonster.Intent.UNKNOWN;
            }
            this.isDone = true;
        }
    }
}
