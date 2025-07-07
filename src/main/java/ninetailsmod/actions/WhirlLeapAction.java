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
    public int[] damage;

    private boolean firstFrame;

    public WhirlLeapAction(AbstractCreature source, int baseDamage, DamageInfo.DamageType type) {
        this.firstFrame = true;
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.baseDamage = baseDamage;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.firstFrame) {
            boolean playedMusic = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            // We need to recalculate the damages in case the monsters' power is updated. (e.g. 3 birds)
            this.damage = DamageInfo.createDamageMatrix(this.baseDamage);
            
            for(int i = 0; i < temp; ++i) {
                AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (!monster.isDying && monster.currentHealth > 0 && !monster.isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect((AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect((AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect));
                    }
                }
            }

            this.firstFrame = false;
        }

        this.tickDuration();
        if (this.isDone) {

            boolean hasDeath = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

            for (int i = 0; i < temp; ++i) {
                AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (!monster.isDeadOrEscaped()) {
                    monster.damage(new DamageInfo(this.source, damage[i], this.damageType));
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
                // Add to top so it won't be affected by pen nip.
                addToTop(new WhirlLeapAction(source, this.baseDamage, this.damageType));
                addToTop(new VFXAction(new DieDieDieEffect(), 0.7F));
            }
        }
    }
}
