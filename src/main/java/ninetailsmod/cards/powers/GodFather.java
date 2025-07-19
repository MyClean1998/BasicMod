package ninetailsmod.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import ninetailsmod.cards.BaseCard;
import ninetailsmod.characters.NineTailsCharacter;
import ninetailsmod.util.CardStats;

public class GodFather extends BaseCard {

    public static final String ID = makeID(GodFather.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            NineTailsCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public GodFather() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setEthereal(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = 0;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, effect)));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, effect)));
            if (!this.freeToPlayOnce)
                p.energy.use(EnergyPanel.totalCount);
        }
    }
}
