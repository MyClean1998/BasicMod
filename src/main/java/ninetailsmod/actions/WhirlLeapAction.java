package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class WhirlLeapAction extends AbstractGameAction {

    public int baseDamage;

    private final float startingDuration = Settings.ACTION_DUR_FAST;

    public WhirlLeapAction(AbstractCreature source, int baseDamage, DamageInfo.DamageType type) {
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.baseDamage = baseDamage;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            dealDamageToAllEnemies();
        }
    }

    private void dealDamageToAllEnemies() {
        addToBot(new VFXAction(new DieDieDieEffect(), 0.7F));

        this.isDone = true;
        boolean hasDeath = false;

        int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

        // We need to recalculate the damages in case the monsters' power is updated. (e.g. 3 birds)
        int[] reCalculatedDamages = DamageInfo.createDamageMatrix(baseDamage);

        for(int i = 0; i < temp; ++i) {
            AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if (!monster.isDeadOrEscaped()) {
                monster.damage(new DamageInfo(this.source, reCalculatedDamages[i], this.damageType));
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, this.attackEffect, true));
                if (monster.isDying) {
                    hasDeath = true;
                }
            }
        }

        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(0.1F));
        }

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }

        if (hasDeath) {
            addToBot(new WhirlLeapAction(source, this.baseDamage, this.damageType));
        }
    }
}
