<?php

$types = [
	'poppy',
];

$blocks = [
	'moobloom_buttercup.png',
];

$colors = [
	'buttercup' => [
		'edba00',
		'faca00',
		'fdd500',

		'fae57d',
		'f7edc1',
	],
	'dandelion' => [
		'f19d25',
		'fed639',
		'ffec4f',

		'f9c67d',
		'f6e0c0',
	],
	'poppy' => [
		'9b221a',
		'bf2529',
		'ed302c',

		'f9847c',
		'f6c4c0'
	],
	'blue_orchid' => [
		'27a9f4',
		'2abffd',
		'2fcefd',

		'7dccf9',
		'c0e3f6',
	],
	'allium' => [
		'7b4ea0',
		'a65ee1',
		'b878ed',

		'c17df9',
		'dec0f6',
	],
	'azure_bluet' => [
		'ffec4f',
		'd6e8e8',
		'f7f7f7',

		'f9ec7c',
		'f6f0c0',
	],
	'red_tulip' => [
		'9b221a',
        'bf2529',
        'ed302c',

        'f9847c',
        'f6c4c0',
	],
	'pink_tulip' => [
		'e4aff4',
		'ebc5fd',
		'f6e2ff',

		'dd7df9',
		'eac0f6',
	],
	'white_tulip' => [
		'9bbdbd',
		'd6e8e8',
		'f7f7f7',

		'7dfafa',
		'c1f7f7',
	],
	'orange_tulip' => [
		'bd6a22',
		'd37d32',
		'f19d25',

		'f9b77d',
		'f7dac1',
	],
	'oxeye_daisy' => [
		'9bbdbd',
    	'd6e8e8',
    	'f7f7f7',
    ],
];

foreach ( $colors as $type => $typeColors ) {
	foreach ( $blocks as $block ) {
		$blockPath = __DIR__.'/input/'.$block;
		$im = imagecreatefrompng($blockPath);
		imagealphablending($im, false);
		for ($x = imagesx($im); $x--;) {
			for ($y = imagesy($im); $y--;) {
				$rgb = imagecolorat($im, $x, $y);
				$currentColorRGB = imagecolorsforindex($im, $rgb);

				$currentColorHEX = sprintf(
					"%02x%02x%02x",
					$currentColorRGB['red'],
					$currentColorRGB['green'],
					$currentColorRGB['blue']
				);

				if(in_array($currentColorHEX, $colors['buttercup'])) {
					$index = array_search( $currentColorHEX, $colors[ 'buttercup' ] );
					$newColorHEX = $colors[ $type ][ $index ];
					$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );
					$colorB = imagecolorallocatealpha( $im, $newColorRGB[ 0 ], $newColorRGB[ 1 ], $newColorRGB[ 2 ], $currentColorRGB[ 'alpha' ] );

					if($colorB === false) {
						continue;
					}

					imagesetpixel( $im, $x, $y, $colorB );
				}
			}
		}

		imageAlphaBlending($im, true);
		imageSaveAlpha($im, true);

		ob_start();
		imagepng($im);
		$imageString = ob_get_clean();
		file_put_contents(
			__DIR__.'/output/moobloom_'.$type.'.png',
			$imageString,
		);

	}
}
