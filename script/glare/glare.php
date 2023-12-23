<?php

ini_set('memory_limit', '2048M');

error_reporting(E_ALL);
ini_set('display_errors', '1');

$memory_limit = ini_get('memory_limit');
echo "New memory limit is: " . $memory_limit . "\n";

$blocks = [
	'new.png',
];


$types = [
	'new',
	'old',
];

$colors = [
	'new' => [
		'0c0f07', // dark green
		'161c0c',
		'44300f', // brown
		'4c3817',
		'945826', //eyes
		'495e27', // green 1
		'50692c', // green 2
		'61722e',
		'6c8031',
		'70922d',
		'84a83c',
		'98c440',
		'd2ebf0', // dark eyes
		'ffffff',
	],
	'old' => [
		'161c0c', // dark green
		'1d2610',
		'44300f', // brown
		'4c3817',
		'44300f', //eyes
		'42552d', // green 1
		'42552d', // green 2
		'495e27',
		'50692c',
		'647233',
		'6c8031',
		'70922d',
		'cec9c0',
		'ebe9e6',
	],
];


foreach ( $colors as $type => $typeColors ) {
	if($type === 'new') {
		continue;
	}

	foreach ( $blocks as $block ) {
		$blockPath = __DIR__.'/input/'.$block;
		$im = imagecreatefrompng($blockPath);
		 imagealphablending($im, false);

		$indexFap = 0;
		$arrayColors = [];

		for ($x = imagesx($im); $x--;) {
			for ($y = imagesy($im); $y--;) {
				$indexFap++;
				$rgb = imagecolorat($im, $x, $y);
				$currentColorRGB = imagecolorsforindex($im, $rgb);

				$currentColorHEX = sprintf(
					"%02x%02x%02x",
					$currentColorRGB['red'],
					$currentColorRGB['green'],
					$currentColorRGB['blue']
				);

				if(in_array($currentColorHEX, $colors['new'])) {
					//print_r($x . "/" . $y . "\n");
					$index = array_search( $currentColorHEX, $colors[ 'new' ] );
					$newColorHEX = $colors[ $type ][ $index ];
					$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );
					//print_r($newColorRGB);
					if(imagecolorstotal($im)>=255) {
						$color = imagecolorclosest($im, $newColorRGB[0], $newColorRGB[1], $newColorRGB[2]);
					} else {
						$colorB = imagecolorallocatealpha($im, $newColorRGB[0], $newColorRGB[1], $newColorRGB[2], $currentColorRGB['alpha']);
						if (isset($arrayColors[$colorB]) === false) {
							$arrayColors[$colorB] = $colorB;
						}
					}

					if ($colorB === false) {
						echo "Failed to allocate color with alpha transparency.\n";
						echo "Current color HEX: $currentColorHEX\n";
						echo "New color HEX: $newColorHEX\n";
						echo "Alpha: " . $currentColorRGB[ 'alpha' ] . "\n";
						continue;
					}


					imagesetpixel( $im, $x, $y, $colorB );
				} else {
					//imagesetpixel( $im, $x, $y, $currentColorRGB );
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
