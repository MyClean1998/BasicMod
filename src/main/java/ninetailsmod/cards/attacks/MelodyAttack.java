package ninetailsmod.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ninetailsmod.cards.BaseCard;
import ninetailsmod.characters.NineTailsCharacter;
import ninetailsmod.util.CardStats;

public class MelodyAttack extends BaseCard {

    public static final String ID = makeID(MelodyAttack.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            NineTailsCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int HP_LOST = 5;
    private static final int UPG_HP_LOST = 2;
    private static final int NUM_HITS = 5;
    private static final int UPG_NUM_HITS = 2;


    public MelodyAttack() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(HP_LOST, UPG_HP_LOST); //Sets the card's damage and how much it changes when upgraded.
        setMagic(NUM_HITS, UPG_NUM_HITS);
        this.damageType = DamageInfo.DamageType.HP_LOSS;
        this.damageTypeForTurn = DamageInfo.DamageType.HP_LOSS;
    }

//    @Override
//    public void calculateCardDamage(AbstractMonster mo) {
//        // No-op, because this attack isn't affected by any bonus.
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.POISON));
        }
    }
}
