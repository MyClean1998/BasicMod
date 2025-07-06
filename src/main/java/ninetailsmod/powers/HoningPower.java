package ninetailsmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import ninetailsmod.BasicMod;
import ninetailsmod.cards.BaseCard;

public class HoningPower extends BasePower {

    public static final String ID = BasicMod.makeID(HoningPower.class.getSimpleName());


    public HoningPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.hasTag(BaseCard.CustomTags.CLAW)) {
            return damage + this.amount;
        }
        return damage;
    }
}
