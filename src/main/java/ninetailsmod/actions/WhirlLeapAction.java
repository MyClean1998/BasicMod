package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhirlLeapAction extends AbstractGameAction {

    public int[] damage;

    private float startingDuration = Settings.ACTION_DUR_FAST;

    public WhirlLeapAction(AbstractCreature source, int[] amount) {
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.damage = amount;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            dealDamageToAllEnemies();
            this.isDone = true;
        }
    }

    private void dealDamageToAllEnemies() {
        addToTop(new DamageAllEnemiesAction(this.source, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (isAnyEnemyDead()) {
            dealDamageToAllEnemies();
        }
    }

    private boolean isAnyEnemyDead() {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (mo.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                return true;
            }
        }
        return false;
    }
}
