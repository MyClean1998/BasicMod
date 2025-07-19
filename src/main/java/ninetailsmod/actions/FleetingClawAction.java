package ninetailsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FleetingClawAction extends AbstractGameAction {

    public int damage;
    private DamageInfo info;
    private final int CARD_DRAWN = 2;

    public FleetingClawAction(AbstractCreature target, DamageInfo info) {
        this.setValues(target, info);
        this.info = info;
        this.attackEffect = AttackEffect.SLASH_VERTICAL;
    }

    public void update() {
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        this.tickDuration();
        if (this.isDone) {
            this.target.damage(info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            if (target.lastDamageTaken > 0) {
                addToBot(new DrawCardAction(CARD_DRAWN));
            }
        }
    }

}
