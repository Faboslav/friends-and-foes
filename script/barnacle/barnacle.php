<?php

$blocks = [
	'input.png',
];

$colors = [
	'input' => [
		'4b6762',
		'506e6a',
		'567c71',
		'61877a',
		'688d80'
	],
	'output' => [
		'3b5e5d',
		'506e6a',
		'49726c',
		'56847e',
		''
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
				}
			}
		}

		imageAlphaBlending($im, true);
		imageSaveAlpha($im, true);

		ob_start();
		imagepng($im);
		$imageString = ob_get_clean();
		file_put_contents(
			__DIR__.'/output/'.$type.'.png',
			$imageString,
		);

	}
}
