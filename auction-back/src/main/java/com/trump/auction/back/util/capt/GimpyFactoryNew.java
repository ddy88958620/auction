package com.trump.auction.back.util.capt;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import redis.clients.jedis.JedisCluster;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.Gimpy;

public class GimpyFactoryNew extends ImageCaptchaFactory {

	private Random myRandom = new SecureRandom();
	private WordToImage wordToImage;
	private WordGenerator wordGenerator;
	private int timeOut;
	@Autowired
	private JedisCluster jedisCluster;
	public static final String BUNDLE_QUESTION_KEY = Gimpy.class.getName();

	public GimpyFactoryNew(WordGenerator generator, WordToImage word2image, int timeOut) {
		if (word2image == null) {
			throw new CaptchaException("Invalid configuration" + " for a GimpyFactory : WordToImage can't be null");
		}
		if (generator == null) {
			throw new CaptchaException("Invalid configuration" + " for a GimpyFactory : WordGenerator can't be null");
		}
		wordToImage = word2image;
		wordGenerator = generator;
		this.timeOut = timeOut;

	}

	/**
	 * gimpies are ImageCaptcha
	 * 
	 * @return the image captcha with default locale
	 */
	public ImageCaptcha getImageCaptcha() {
		return getImageCaptcha(Locale.getDefault());
	}

	public WordToImage getWordToImage() {
		return wordToImage;
	}

	public WordGenerator getWordGenerator() {
		return wordGenerator;
	}

	/**
	 * gimpies are ImageCaptcha
	 * 
	 * @return a pixCaptcha with the question :"spell the word"
	 */
	public ImageCaptcha getImageCaptcha(Locale locale) {

		// length
		Integer wordLength = getRandomLength();

		String word = getWordGenerator().getWord(wordLength, locale);

		BufferedImage image = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String captchaId = request.getSession().getId();
			jedisCluster.setex(captchaId, timeOut, word.toLowerCase());
			image = getWordToImage().getImage(word);
		} catch (Throwable e) {
			throw new CaptchaException(e);
		}

		ImageCaptcha captcha = new GimpyNew(CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY), image, word);
		return captcha;
	}

	protected Integer getRandomLength() {
		Integer wordLength;
		int range = getWordToImage().getMaxAcceptedWordLength() - getWordToImage().getMinAcceptedWordLength();
		int randomRange = range != 0 ? myRandom.nextInt(range + 1) : 0;
		wordLength = new Integer(randomRange + getWordToImage().getMinAcceptedWordLength());
		return wordLength;
	}
}
