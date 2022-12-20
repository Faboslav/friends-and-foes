<?php

$types = [
	'birch',
	'spruce',
	'jungle',
	'acacia',
	'dark_oak',
	'crimson',
	'warped',
	'mangrove',
];

$blocks = [
	'beehive_end.png',
	'beehive_front.png',
	'beehive_front_honey.png',
	'beehive_side.png',
];

$colors = [
	'oak' => [
		'67502c',
		'7e6237',
		'967441',
		'9f844d',
		'af8f55',
		'b8945f',
		'c29d62',

		'8a6938',
		'987e47',
	],
	'birch' => [
		'9e8b61',
		'a59467',
		'ae9f76',
		'b8a875',
		'c8b77a',
		'd7c185',
		'd7cb8d',
	],
	'spruce' => [
		'553a1f',
		'5a4424',
		'614b2e',
		'70522e',
		'785836',
		'7a5a34',
		'82613a',
	],
	'jungle' => [
		'68462f',
		'785437',
		'976a44',
		'9f714a',
		'aa7954',
		'b88764',
		'bf8e6b',
	],
	'acacia' => [
		'884728',
		'8f4c2a',
		'99502b',
		'a05630',
		'ad5d32',
		'ba6337',
		'c26d3f',
	],
	'dark_oak' => [
		'291a0c',
		'301e0e',
		'3a2411',
		'3e2912',
		'492f17',
		'4f3218',
		'53381a',
	],
	'crimson' => [
		'3f1e2d',
		'442131',
		'4b2737',
		'5c3042',
		'6a344b',
		'7e3a56',
		'863e5a',
	],
	'warped' => [
		'113835',
		'1e4340',
		'1f5752',
		'2e5f51',
		'287067',
		'398382',
		'3a8e8c',
	],
	'mangrove' => [
		'5d1c1e',
		'642423',
		'6f2a2d',
		'753136',
		'773934',
		'7f4234',
		'8b4d3a',
	],
];

foreach ( $types as $type ) {
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

				if(in_array($currentColorHEX, $colors['oak'])) {
					// handle inconsistent colors
					if($currentColorHEX == '8a6938') {
						$newColorHEX = $colors[ 'oak' ][ 1 ];
						$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );

						$currentColorRGBSubtract = min($currentColorRGB);
						$newColorRGBSubtract = min($newColorRGB);

						$currentColorSubtracted = [
							$currentColorRGB['red'] - $currentColorRGBSubtract,
							$currentColorRGB['green'] - $currentColorRGBSubtract,
							$currentColorRGB['blue'] - $currentColorRGBSubtract,
						];

						$newColorSubtracted = [
							$newColorRGB[0] - $newColorRGBSubtract,
							$newColorRGB[1] - $newColorRGBSubtract,
							$newColorRGB[2] - $newColorRGBSubtract,
						];

						$redDifference = $currentColorSubtracted[0] - $newColorRGB[0];
						$greenDifference = $currentColorSubtracted[1] - $newColorRGB[1];
						$blueDifference = $currentColorSubtracted[2] - $newColorRGB[2];

						$newColorHEX = $colors[ $type ][ 1 ];
						$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );

						$newColorRGB = [
							$newColorRGB[0] + $redDifference,
							$newColorRGB[1] + $greenDifference,
							$newColorRGB[2] + $blueDifference,
						];

						$colorB = imagecolorallocatealpha( $im, $newColorRGB[ 0 ], $newColorRGB[ 1 ], $newColorRGB[ 2 ], $currentColorRGB[ 'alpha' ] );
						imagesetpixel( $im, $x, $y, $colorB );
					} elseif($currentColorHEX == '987e47') {
						$newColorHEX = $colors[ 'oak' ][ 1 ];
						$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );

						$currentColorRGBSubtract = min($currentColorRGB);
						$newColorRGBSubtract = min($newColorRGB);

						$currentColorSubtracted = [
							$currentColorRGB['red'] - $currentColorRGBSubtract,
							$currentColorRGB['green'] - $currentColorRGBSubtract,
							$currentColorRGB['blue'] - $currentColorRGBSubtract,
						];

						$newColorSubtracted = [
							$newColorRGB[0] - $newColorRGBSubtract,
							$newColorRGB[1] - $newColorRGBSubtract,
							$newColorRGB[2] - $newColorRGBSubtract,
						];

						$redDifference = $currentColorSubtracted[0] - $newColorRGB[0];
						$greenDifference = $currentColorSubtracted[1] - $newColorRGB[1];
						$blueDifference = $currentColorSubtracted[2] - $newColorRGB[2];

						$newColorHEX = $colors[ $type ][ 1 ];
						$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );

						$newColorRGB = [
							$newColorRGB[0] + $redDifference,
							$newColorRGB[1] + $greenDifference,
							$newColorRGB[2] + $blueDifference,
						];

						$colorB = imagecolorallocatealpha( $im, $newColorRGB[ 0 ], $newColorRGB[ 1 ], $newColorRGB[ 2 ], $currentColorRGB[ 'alpha' ] );
						imagesetpixel( $im, $x, $y, $colorB );
					} else {
						$index = array_search( $currentColorHEX, $colors[ 'oak' ] );
						$newColorHEX = $colors[ $type ][ $index ];
						$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );
						$colorB = imagecolorallocatealpha( $im, $newColorRGB[ 0 ], $newColorRGB[ 1 ], $newColorRGB[ 2 ], $currentColorRGB[ 'alpha' ] );
						imagesetpixel( $im, $x, $y, $colorB );
					}
				}
			}
		}

		imageAlphaBlending($im, true);
		imageSaveAlpha($im, true);

		ob_start();
		imagepng($im);
		$imageString = ob_get_clean();
		file_put_contents(
			__DIR__.'/output/'.$type . '_' . $block,
			$imageString,
		);

	}
}
