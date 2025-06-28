package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class WhirlLeapAction extends AbstractGameAction {

    public int[] damage;
    private DamageInfo info;

    private float startingDuration = Settings.ACTION_DUR_FAST;
    private ArrayList<AbstractMonster> monstersAlive = new ArrayList<>();

    public WhirlLeapAction(AbstractCreature source, DamageInfo info) {
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.info = info;
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.monstersAlive.add(mo);
            }
        }
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            dealDamageToAllEnemies();
        }
    }

    private void dealDamageToAllEnemies() {
        if (!monstersAlive.isEmpty()) {
            addToBot(new VFXAction(new DieDieDieEffect(), 0.7F));
        }
        this.isDone = true;
        boolean hasDeath = false;

        for (AbstractMonster monster: monstersAlive) {
            if (!monster.isDying && monster.currentHealth > 0 && !monster.isEscaping) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, this.attackEffect, true));
                monster.damage(this.info);
                if (monster.isDying) {
                    hasDeath = true;
                }
            }
        }

        if (hasDeath) {
            addToBot(new WhirlLeapAction(source, this.info));
        }
    }
}
