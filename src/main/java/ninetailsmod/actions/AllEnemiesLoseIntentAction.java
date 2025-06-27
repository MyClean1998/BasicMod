package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ninetailsmod.powers.LoseIntentPower;


public class AllEnemiesLoseIntentAction extends AbstractGameAction {

    private float startingDuration = Settings.ACTION_DUR_FAST;

    public AllEnemiesLoseIntentAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                addToTop(new ApplyPowerAction(mo, source, new LoseIntentPower(mo, 1), 1));
            }
            this.isDone = true;
        }
    }
}
