#include "scripts.h"

void run_script_user2()
{
    set_weapon(WEAPON_PISTOL);

    size_t enemy = get_nearest_enemy();

    use_weapon(enemy);

    move_away_from(enemy);    
}
