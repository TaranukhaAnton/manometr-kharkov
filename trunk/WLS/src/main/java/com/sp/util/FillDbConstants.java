package com.sp.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexander
 */
public class FillDbConstants {
   


// imei -> id
public static final Map<String,Integer> imeiMap = new HashMap<String,Integer>();
// imei -> unit id
public static final Map<String,Integer> imeiUnitMap = new HashMap<String,Integer>();
public static final Set<String> imeiSet = new HashSet<String>();

static {
    //  imeis => id
    /* gisglos start*/
    imeiMap.put("352848027240437", 3417);
    imeiMap.put("352848027915624", 3355);
    imeiMap.put("352848027941471", 3424);
    imeiMap.put("352848028224448", 3357);
    imeiMap.put("352848029036791", 3415);
    imeiMap.put("352848029180003", 3425);
    imeiMap.put("352848029216641", 3416);
    imeiMap.put("352848029217847", 3432);
    imeiMap.put("352848029223399", 3423);
    imeiMap.put("352848029227903", 3433);
    imeiMap.put("352848029236003", 3414);
    imeiMap.put("352848029237845", 3427);
    imeiMap.put("352848029238496", 3426);
    imeiMap.put("352848029239395", 3413);
//        imei = > unitId
    imeiUnitMap.put("352848027915624", 3410);
    imeiUnitMap.put("352848028224448", 3412);
    imeiUnitMap.put("352848029239395", 3468);
    imeiUnitMap.put("352848029236003", 3469);
    imeiUnitMap.put("352848029036791", 3470);
    imeiUnitMap.put("352848029216641", 3471);
    imeiUnitMap.put("352848027240437", 3472);
    imeiUnitMap.put("352848029223399", 3478);
    imeiUnitMap.put("352848027941471", 3479);
    imeiUnitMap.put("352848029180003", 3480);
    imeiUnitMap.put("352848029238496", 3481);
    imeiUnitMap.put("352848029237845", 3482);
    imeiUnitMap.put("352848029217847", 3487);
    imeiUnitMap.put("352848029227903", 3488);
    /* gisglos end*/

    /*gloucestershirehospitalsnhsfou start */

    imeiMap.put("352848027496682", 3279);
    imeiMap.put("352848027497011", 3283);
    imeiMap.put("352848027841697", 3296);
    imeiMap.put("352848027841903", 3288);
    imeiMap.put("352848027843651", 3284);
    imeiMap.put("352848027843842", 3278);
    imeiMap.put("352848027843883", 3304);
    imeiMap.put("352848027843891", 3274);
    imeiMap.put("352848027843941", 3281);
    imeiMap.put("352848027843958", 3273);
    imeiMap.put("352848027862172", 3292);
    imeiMap.put("352848027862180", 3289);
    imeiMap.put("352848027864855", 3298);
    imeiMap.put("352848027864863", 3293);
    imeiMap.put("352848027865100", 3285);
    imeiMap.put("352848027879838", 3286);
    imeiMap.put("352848027880588", 3269);
    imeiMap.put("352848027889340", 3301);
    imeiMap.put("352848027928734", 3291);
    imeiMap.put("352848027928791", 3294);
    imeiMap.put("352848027928874", 3290);
    imeiMap.put("352848027929435", 3287);
    imeiMap.put("352848027929500", 3276);
    imeiMap.put("352848027929534", 3275);
    imeiMap.put("352848027935093", 3297);
    imeiMap.put("352848027983903", 3295);
    imeiMap.put("352848027983986", 3280);
    imeiMap.put("352848027984174", 3305);
    imeiMap.put("352848028054258", 3282);
    imeiMap.put("352848028054316", 3271);
    imeiMap.put("352848028175020", 3299);
    imeiMap.put("352848028200224", 3277);
    imeiMap.put("352848028210322", 3302);
    imeiMap.put("352848028210348", 3272);
    imeiMap.put("352848028212286", 3300);

    imeiUnitMap.put("352848027880588", 3324);
    imeiUnitMap.put("352848028054316", 3326);
    imeiUnitMap.put("352848028210348", 3327);
    imeiUnitMap.put("352848027843958", 3328);
    imeiUnitMap.put("352848027843891", 3329);
    imeiUnitMap.put("352848027929534", 3330);
    imeiUnitMap.put("352848027929500", 3331);
    imeiUnitMap.put("352848028200224", 3332);
    imeiUnitMap.put("352848027843842", 3333);
    imeiUnitMap.put("352848027496682", 3334);
    imeiUnitMap.put("352848027983986", 3335);
    imeiUnitMap.put("352848027843941", 3336);
    imeiUnitMap.put("352848028054258", 3337);
    imeiUnitMap.put("352848027497011", 3338);
    imeiUnitMap.put("352848027843651", 3339);
    imeiUnitMap.put("352848027865100", 3340);
    imeiUnitMap.put("352848027879838", 3341);
    imeiUnitMap.put("352848027929435", 3342);
    imeiUnitMap.put("352848027841903", 3343);
    imeiUnitMap.put("352848027862180", 3344);
    imeiUnitMap.put("352848027928874", 3345);
    imeiUnitMap.put("352848027928734", 3346);
    imeiUnitMap.put("352848027862172", 3347);
    imeiUnitMap.put("352848027864863", 3348);
    imeiUnitMap.put("352848027928791", 3349);
    imeiUnitMap.put("352848027983903", 3350);
    imeiUnitMap.put("352848027841697", 3351);
    imeiUnitMap.put("352848027935093", 3352);
    imeiUnitMap.put("352848027864855", 3353);
    imeiUnitMap.put("352848028175020", 3354);
    imeiUnitMap.put("352848028212286", 3355);
    imeiUnitMap.put("352848027889340", 3356);
    imeiUnitMap.put("352848028210322", 3357);
    imeiUnitMap.put("352848027843883", 3359);
    imeiUnitMap.put("352848027984174", 3360);

    /*gloucestershirehospitalsnhsfou end */

    /*hendyhire start */
    imeiMap.put("352848027838297", 3327);
    imeiMap.put("352848027844030", 3313);
    imeiMap.put("352848027862248", 3316);
    imeiMap.put("352848027863048", 3314);
    imeiMap.put("352848027885173", 3325);
    imeiMap.put("352848027888102", 3326);
    imeiMap.put("352848027904230", 3306);
    imeiMap.put("352848027929187", 3318);
    imeiMap.put("352848027929328", 3317);
    imeiMap.put("352848027994710", 3328);
    imeiMap.put("352848027999677", 3310);
    imeiMap.put("352848028040620", 3309);
    imeiMap.put("352848028041362", 3307);
    imeiMap.put("352848028083513", 3353);
    imeiMap.put("352848028167100", 3341);
    imeiMap.put("352848028193700", 3311);
    imeiMap.put("352848028202386", 3324);
    imeiMap.put("352848028202618", 3308);

    imeiUnitMap.put("352848027904230", 3361);
    imeiUnitMap.put("352848028041362", 3362);
    imeiUnitMap.put("352848028202618", 3363);
    imeiUnitMap.put("352848028040620", 3364);
    imeiUnitMap.put("352848027999677", 3365);
    imeiUnitMap.put("352848028193700", 3366);
    imeiUnitMap.put("352848027844030", 3368);
    imeiUnitMap.put("352848027863048", 3369);
    imeiUnitMap.put("352848027862248", 3371);
    imeiUnitMap.put("352848027929328", 3372);
    imeiUnitMap.put("352848027929187", 3373);
    imeiUnitMap.put("352848028202386", 3379);
    imeiUnitMap.put("352848027885173", 3380);
    imeiUnitMap.put("352848027888102", 3381);
    imeiUnitMap.put("352848027838297", 3382);
    imeiUnitMap.put("352848027994710", 3383);
    imeiUnitMap.put("352848028167100", 3396);
    imeiUnitMap.put("352848028083513", 3408);


    /*hendyhire end */

    /*protracksolutions start */

    imeiUnitMap.put("352848027820618", 3325);
    imeiUnitMap.put("352848025847472", 3358);
    imeiUnitMap.put("352848028057095", 3377);
    imeiUnitMap.put("352848027804166", 3389);
    imeiUnitMap.put("352848027854823", 3390);
    imeiUnitMap.put("352848027993274", 3391);
    imeiUnitMap.put("352848025919602", 3422);
    imeiUnitMap.put("352848029240567", 3455);

    imeiMap.put("352848025847472", 3303);
    imeiMap.put("352848025919602", 3367);
    imeiMap.put("352848027804166", 3334);
    imeiMap.put("352848027820618", 3270);
    imeiMap.put("352848027854823", 3335);
    imeiMap.put("352848027993274", 3336);
    imeiMap.put("352848028057095", 3322);
    imeiMap.put("352848029240567", 3400);

    /*protracksolutions end */

    /*skmodernheatingltd start*/

    imeiMap.put("352848027915442", 3337);
    imeiMap.put("352848028061428", 3333);
    imeiMap.put("352848028061873", 3332);
    imeiMap.put("352848028063259", 3338);
    imeiMap.put("352848029273261", 3395);

    imeiUnitMap.put("352848028061873", 3387);
    imeiUnitMap.put("352848028061428", 3388);
    imeiUnitMap.put("352848027915442", 3392);
    imeiUnitMap.put("352848028063259", 3393);
    imeiUnitMap.put("352848029273261", 3450);

    /*skmodernheatingltd end*/

    /*templeconstruction start*/

    imeiUnitMap.put("352848028142491", 3411);
    imeiUnitMap.put("352848027945209", 3413);
    imeiUnitMap.put("352848029078199", 3417);
    imeiUnitMap.put("352848028776439", 3418);
    imeiUnitMap.put("352848028777601", 3419);
    imeiUnitMap.put("352848028781397", 3420);
    imeiUnitMap.put("352848028733315", 3421);
    imeiUnitMap.put("352848025919602", 3422);
    imeiUnitMap.put("352848029383144", 3423);
    imeiUnitMap.put("352848029193253", 3424);
    imeiUnitMap.put("352848029111339", 3449);
    imeiUnitMap.put("352848029369689", 3466);

    imeiMap.put("352848025919602", 3367);
    imeiMap.put("352848027945209", 3358);
    imeiMap.put("352848028142491", 3356);
    imeiMap.put("352848028733315", 3366);
    imeiMap.put("352848028776439", 3363);
    imeiMap.put("352848028777601", 3364);
    imeiMap.put("352848028781397", 3365);
    imeiMap.put("352848029078199", 3362);
    imeiMap.put("352848029111339", 3394);
    imeiMap.put("352848029193253", 3369);
    imeiMap.put("352848029369689", 3411);
    imeiMap.put("352848029383144", 3368);

    /*templeconstruction end*/

    /*tithegrovelimited start*/

    imeiMap.put("352848027854476", 3331);
    imeiMap.put("352848027864079", 3315);
    imeiMap.put("352848027878863", 3312);
    imeiMap.put("352848027883319", 3352);
    imeiMap.put("352848027883806", 3321);
    imeiMap.put("352848027903463", 3323);
    imeiMap.put("352848027915483", 3360);
    imeiMap.put("352848027916382", 3361);
    imeiMap.put("352848027921358", 3339);
    imeiMap.put("352848027940986", 3350);
    imeiMap.put("352848027983002", 3329);
    imeiMap.put("352848027990429", 3319);
    imeiMap.put("352848027993449", 3359);
    imeiMap.put("352848027995725", 3344);
    imeiMap.put("352848027999891", 3342);
    imeiMap.put("352848028000863", 3340);
    imeiMap.put("352848028038251", 3320);
    imeiMap.put("352848028060859", 3349);
    imeiMap.put("352848028061865", 3348);
    imeiMap.put("352848028083513", 3353);
    imeiMap.put("352848028166128", 3343);
    imeiMap.put("352848028221386", 3354);
    imeiMap.put("352848028224430", 3346);
    imeiMap.put("352848028225049", 3351);
    imeiMap.put("352848028229272", 3330);
    imeiMap.put("352848028241384", 3347);
    imeiMap.put("352848028280226", 3345);
    imeiMap.put("352848029020985", 3376);
    imeiMap.put("352848029027428", 3401);
    imeiMap.put("352848029031842", 3374);
    imeiMap.put("352848029041254", 3409);
    imeiMap.put("352848029041494", 3397);
    imeiMap.put("352848029042682", 3396);
    imeiMap.put("352848029044936", 3407);
    imeiMap.put("352848029046329", 3377);
    imeiMap.put("352848029049018", 3408);
    imeiMap.put("352848029050859", 3404);
    imeiMap.put("352848029065287", 3388);
    imeiMap.put("352848029072366", 3403);
    imeiMap.put("352848029095532", 3373);
    imeiMap.put("352848029097140", 3387);
    imeiMap.put("352848029097272", 3391);
    imeiMap.put("352848029097496", 3381);
    imeiMap.put("352848029097587", 3390);
    imeiMap.put("352848029105950", 3389);
    imeiMap.put("352848029107329", 3379);
    imeiMap.put("352848029107733", 3393);
    imeiMap.put("352848029119241", 3406);
    imeiMap.put("352848029141658", 3405);
    imeiMap.put("352848029142110", 3382);
    imeiMap.put("352848029142201", 3383);
    imeiMap.put("352848029143704", 3402);
    imeiMap.put("352848029147432", 3370);
    imeiMap.put("352848029180284", 3371);
    imeiMap.put("352848029210735", 3429);
    imeiMap.put("352848029211030", 3430);
    imeiMap.put("352848029214497", 3385);
    imeiMap.put("352848029225949", 3428);
    imeiMap.put("352848029228687", 3431);
    imeiMap.put("352848029235690", 3378);
    imeiMap.put("352848029235716", 3386);
    imeiMap.put("352848029242316", 3384);
    imeiMap.put("352848029257512", 3380);
    imeiMap.put("352848029273493", 3399);
    imeiMap.put("352848029273600", 3375);
    imeiMap.put("352848029274327", 3372);
    imeiMap.put("352848029389349", 3410);

    imeiUnitMap.put("352848027878863", 3367);
    imeiUnitMap.put("352848027864079", 3370);
    imeiUnitMap.put("352848027990429", 3374);
    imeiUnitMap.put("352848028038251", 3375);
    imeiUnitMap.put("352848027883806", 3376);
    imeiUnitMap.put("352848027903463", 3378);
    imeiUnitMap.put("352848027983002", 3384);
    imeiUnitMap.put("352848028229272", 3385);
    imeiUnitMap.put("352848027854476", 3386);
    imeiUnitMap.put("352848027921358", 3394);
    imeiUnitMap.put("352848028000863", 3395);
    imeiUnitMap.put("352848027999891", 3397);
    imeiUnitMap.put("352848028166128", 3398);
    imeiUnitMap.put("352848027995725", 3399);
    imeiUnitMap.put("352848028280226", 3400);
    imeiUnitMap.put("352848028224430", 3401);
    imeiUnitMap.put("352848028241384", 3402);
    imeiUnitMap.put("352848028061865", 3403);
    imeiUnitMap.put("352848028060859", 3404);
    imeiUnitMap.put("352848027940986", 3405);
    imeiUnitMap.put("352848028225049", 3406);
    imeiUnitMap.put("352848027883319", 3407);
    imeiUnitMap.put("352848028083513", 3408);
    imeiUnitMap.put("352848028221386", 3409);
    imeiUnitMap.put("352848027993449", 3414);
    imeiUnitMap.put("352848027915483", 3415);
    imeiUnitMap.put("352848027916382", 3416);
    imeiUnitMap.put("352848029147432", 3425);
    imeiUnitMap.put("352848029180284", 3426);
    imeiUnitMap.put("352848029274327", 3427);
    imeiUnitMap.put("352848029095532", 3428);
    imeiUnitMap.put("352848029031842", 3429);
    imeiUnitMap.put("352848029273600", 3430);
    imeiUnitMap.put("352848029020985", 3431);
    imeiUnitMap.put("352848029046329", 3432);
    imeiUnitMap.put("352848029235690", 3433);
    imeiUnitMap.put("352848029107329", 3434);
    imeiUnitMap.put("352848029257512", 3435);
    imeiUnitMap.put("352848029097496", 3436);
    imeiUnitMap.put("352848029142110", 3437);
    imeiUnitMap.put("352848029142201", 3438);
    imeiUnitMap.put("352848029242316", 3439);
    imeiUnitMap.put("352848029214497", 3440);
    imeiUnitMap.put("352848029235716", 3441);
    imeiUnitMap.put("352848029097140", 3442);
    imeiUnitMap.put("352848029065287", 3443);
    imeiUnitMap.put("352848029105950", 3444);
    imeiUnitMap.put("352848029097587", 3445);
    imeiUnitMap.put("352848029097272", 3446);
    imeiUnitMap.put("352848029107733", 3448);
    imeiUnitMap.put("352848029042682", 3451);
    imeiUnitMap.put("352848029041494", 3452);
    imeiUnitMap.put("352848029273493", 3454);
    imeiUnitMap.put("352848029027428", 3456);
    imeiUnitMap.put("352848029143704", 3457);
    imeiUnitMap.put("352848029072366", 3458);
    imeiUnitMap.put("352848029050859", 3459);
    imeiUnitMap.put("352848029141658", 3460);
    imeiUnitMap.put("352848029119241", 3461);
    imeiUnitMap.put("352848029044936", 3462);
    imeiUnitMap.put("352848029049018", 3463);
    imeiUnitMap.put("352848029041254", 3464);
    imeiUnitMap.put("352848029389349", 3465);
    imeiUnitMap.put("352848029225949", 3483);
    imeiUnitMap.put("352848029210735", 3484);
    imeiUnitMap.put("352848029211030", 3485);
    imeiUnitMap.put("352848029228687", 3486);

    /*tithegrovelimited end*/



}


}
