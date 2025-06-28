package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class WhirlLeapAction extends AbstractGameAction {

    public int[] damage;
    private DamageInfo info;

    private float startingDuration = Settings.ACTION_DUR_FAST;
    private ArrayList<AbstractMonster> currentMonsters = new ArrayList<>();

    public WhirlLeapAction(AbstractCreature source, int[] amount) {
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.damage = amount;
    }

    public WhirlLeapAction(AbstractCreature source, DamageInfo info) {
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.info = info;
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.currentMonsters.add(mo);
            }
        }
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            dealDamageToAllEnemies();
            this.isDone = true;
        }
    }

    private void dealDamageToAllEnemies() {
//        addToTop(new VFXAction(new DieDieDieEffect(), 0.7F));
        int size = this.currentMonsters.size();
        for(int i = 0; i < size; ++i) {
            if (!(this.currentMonsters.get(i)).isDying && (this.currentMonsters.get(i)).currentHealth > 0 && !(this.currentMonsters.get(i)).isEscaping) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).damage(this.info);
            }
        }
        if (isAnyEnemyDead()) {
            dealDamageToAllEnemies();
        }
    }

    private boolean isAnyEnemyDead() {
        boolean isAnyEnemyDead = false;
        ArrayList<AbstractMonster> temp = new ArrayList<>();
        for (AbstractMonster mo : this.currentMonsters) {
            if ((mo.isDying || mo.currentHealth <= 0) && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                isAnyEnemyDead = true;
            } else {
                temp.add(mo);
            }
        }
        this.currentMonsters = temp;
        return isAnyEnemyDead;
    }

}
