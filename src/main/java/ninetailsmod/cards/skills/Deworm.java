package ninetailsmod.cards.skills;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ninetailsmod.actions.ExhaustAllStatusAndCurseAction;
import ninetailsmod.cards.BaseCard;
import ninetailsmod.characters.NineTailsCharacter;
import ninetailsmod.util.CardStats;

public class Deworm extends BaseCard {

    public static final String ID = makeID(Deworm.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            NineTailsCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Deworm() {
        super(ID, info);
        setCostUpgrade(0);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = p.hand.getCardsOfType(CardType.STATUS).size() + p.hand.getCardsOfType(CardType.CURSE).size();
        addToBot(new ExhaustAllStatusAndCurseAction());
        addToBot(new DrawCardAction(p, count));
    }
}
