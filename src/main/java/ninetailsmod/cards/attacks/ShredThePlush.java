package ninetailsmod.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import ninetailsmod.cards.BaseCard;
import ninetailsmod.characters.NineTailsCharacter;
import ninetailsmod.util.CardStats;

import static ninetailsmod.cards.attacks.FrenziedClaw.isClaw;

public class ShredThePlush extends BaseCard {

    public static final String ID = makeID(ShredThePlush.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            NineTailsCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int NUM_CLAWS = 3;
    private static final int UPG_NUM_CLAWS= -1;
    private static final int ATTACK_TIMES = 6;

    public ShredThePlush() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(10);
        setMagic(NUM_CLAWS, UPG_NUM_CLAWS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
        } else {
            addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
        }
        for (int i = 0; i < ATTACK_TIMES; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (countClawsInHand() > this.magicNumber)
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;
        if (countClawsInHand() < this.magicNumber) {
            this.cantUseMessage = cardStrings.UPGRADE_DESCRIPTION;
            return false;
        }
        return canUse;
    }

    private static int countClawsInHand() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isClaw(c))
                count++;
        }
        return count;
    }
}
