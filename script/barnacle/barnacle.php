<?php

$blocks = [
	'input.png',
];

$colors = [
	'input' => [
		'4b6762',
		'567c71',
		'506e6a',
		'61877a',

		// eyes
		'000000',
		'ffffff',

		// tooth
		'b4afa6',
		'ebe9e6'
	],
	'output' => [
		'27514b',
		'3b5e5d',
		'49726c',
		'49726c',
		//'56847e',

		// eyes
		'000000',
		'ffffff',
		// tooth
		'65e0de',
		'8ff2d7'
	],
];

foreach ( $colors as $type => $typeColors ) {
	if($type === 'input') {
		continue;
	}

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

				if(in_array($currentColorHEX, $colors['input'])) {
					$index = array_search( $currentColorHEX, $colors[ 'input' ] );
					$newColorHEX = $colors[ $type ][ $index ];
					$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );
					$colorB = imagecolorallocatealpha( $im, $newColorRGB[ 0 ], $newColorRGB[ 1 ], $newColorRGB[ 2 ], $currentColorRGB[ 'alpha' ] );

					if($colorB === false) {
						continue;
					}

					imagesetpixel( $im, $x, $y, $colorB );
				} else {
					$newColorHEX = '56847e';
					$random = rand(1, 50);

					if($random == 1) {
						$newColorHEX = '54b972';
					} else if($random == 2) {
						$newColorHEX = '4ca682';
					} else if($random == 10 || $random == 11 || $random == 12) {
						$newColorHEX = '49726c';
					} else if($random == 20 || $random == 21 || $random == 22) {
						$newColorHEX = '3b5e5d';
					}

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
			__DIR__.'/output/'.$type.'2.png',
			$imageString,
		);

	}
}
