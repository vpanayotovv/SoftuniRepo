package viceCity.core;

import viceCity.common.ConstantMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.LinkedList;
import java.util.List;

public class ControllerImpl implements Controller {
    private final Player mainPlayer;
    private final List<Player> civilPlayer;
    private final List<Gun> guns;
    private final Neighbourhood neighbourhood;

    public ControllerImpl() {
        this.neighbourhood = new GangNeighbourhood();
        this.mainPlayer = new MainPlayer();
        this.civilPlayer = new LinkedList<>();
        this.guns = new LinkedList<>();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        this.civilPlayer.add(player);
        return String.format(ConstantMessages.PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun;
        if (Pistol.class.getSimpleName().equals(type)) {
            gun = new Pistol(name);
        } else if (Rifle.class.getSimpleName().equals(type)) {
            gun = new Rifle(name);
        } else {
            return ConstantMessages.GUN_TYPE_INVALID;
        }

        this.guns.add(gun);
        return String.format(ConstantMessages.GUN_ADDED, name, type);
    }

    @Override
    public String addGunToPlayer(String name) {
        if (this.guns.isEmpty()) {
            return ConstantMessages.GUN_QUEUE_IS_EMPTY;
        }
        String message;
        Gun gun = this.guns.get(0);
        Player civilPlayer = this.civilPlayer.stream().filter(p -> p.getName().equals(name))
                .findFirst().orElse(null);
        if ("Vercetti".equals(name)) {
            message = String.format(ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER, gun.getName(), "Tommy Vercetti");
            this.mainPlayer.getGunRepository().add(gun);
            this.guns.remove(gun);
        } else if (civilPlayer != null) {
            message = String.format(ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), name);
            civilPlayer.getGunRepository().add(gun);
            this.guns.remove(gun);
        } else {
            message = ConstantMessages.CIVIL_PLAYER_DOES_NOT_EXIST;
        }
        return message;
    }

    @Override
    public String fight() {
        int mainPlayerLifePoints = this.mainPlayer.getLifePoints();
        long civilPlayersAliveCount = this.civilPlayer.stream().filter(Player::isAlive).count();

        this.neighbourhood.action(this.mainPlayer,this.civilPlayer);

        int mainPlayerLifePointsAfterFight = this.mainPlayer.getLifePoints();
        long civilPlayersAliveCountAfterFight = this.civilPlayer.stream().filter(Player::isAlive).count();

        String message;
        StringBuilder sb = new StringBuilder();
        if (mainPlayerLifePoints == mainPlayerLifePointsAfterFight
                && civilPlayersAliveCount == civilPlayersAliveCountAfterFight){
            message = ConstantMessages.FIGHT_HOT_HAPPENED;

        } else {
            sb.append(ConstantMessages.FIGHT_HAPPENED)
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.MAIN_PLAYER_LIVE_POINTS_MESSAGE,mainPlayerLifePointsAfterFight))
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE,civilPlayersAliveCount - civilPlayersAliveCountAfterFight))
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.CIVIL_PLAYERS_LEFT_MESSAGE,civilPlayersAliveCountAfterFight));
            message = sb.toString();

        }
        return message;
    }
}
